package com.example.exercisecomposeapi.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exercisecomposeapi.data.local.GroupOneEntity
import com.example.exercisecomposeapi.ui.screens.ExerciseViewModel
import com.example.exercisecomposeapi.utils.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: ExerciseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    
                }
                App(vm)
            }
        }
    }
}

@Composable
fun App(vm: ExerciseViewModel = hiltViewModel()) {
    val data by vm.userListData.observeAsState(Resource.Idle())

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row {
                    Text(text = "Compose App")
                }
            })
    }) {
        FilterByCity(vm = vm)

        Spacer(modifier = Modifier.height(5.dp))
        
        ContentList(resource = data) {
            vm.refresh()
        }

    }
}

@Composable
fun FilterByCity(vm: ExerciseViewModel) {
    val filter by vm.search.observeAsState("")
    val focusManager = LocalFocusManager.current
    val clearText by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = filter,
                onValueChange = { value ->
                    vm.change(value)
                },
                placeholder = {
                    Text(text = "Filter By City")
                },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .padding(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                trailingIcon = {
                    AnimatedVisibility(
                        visible = clearText,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { vm.searchClear() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear"
                            )
                        }

                    }
                }
            )
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    vm.searchData()
                },
                enabled = filter.isNotEmpty()
            ) {
                Icon(Icons.Default.Search, contentDescription = "")
            }
        }
        Column(modifier = Modifier.padding(5.dp)) {
            Row(modifier = Modifier.padding(3.dp)) {
                Button(onClick = { vm.clearFilter() }) {
                    Text(text = "Clear Filter")
                }
                Button(onClick = { vm.sortData() }) {
                    Text(text = "Sort")
                    
                }
            }
        }    
        }
    }
}

@Composable
fun Content(groupOne: GroupOneEntity){
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${groupOne.name} ${groupOne.surname}, ",
                )
                Box(
                    modifier = Modifier.background(
                        shape = RoundedCornerShape(50.dp),
                    color = Color(0xFFFFFFF5)
                    )
                ) {
                    Text(
                        text = "$groupOne.age}",
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(horizontal = 12.dp),
                    )
                }
            }
            Text(
                text = groupOne.city,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ContentList(resource: Resource<List<GroupOneEntity>?>, refresh: () -> Unit) {
    val swipeRefreshState = rememberSwipeRefreshState(resource !is Resource.Loading)

    SwipeRefresh(state = swipeRefreshState, onRefresh = refresh) {
        when (resource) {
            is Resource.Failure -> {
                swipeRefreshState.isRefreshing = false
                ContentStateView(resource.message ?: "") {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            }
            is Resource.Success -> {
                swipeRefreshState.isRefreshing = false
                val data = resource.data!!

                if (data.isEmpty())
                    ContentStateView(text = "The list is empty") {
                        Icon(Icons.Default.Warning, contentDescription = "")
                        
                    }
                else
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(data){groupOne ->
                            Content(groupOne)
                        }
                    }
            }
            is Resource.Loading -> {
                swipeRefreshState.isRefreshing = false
                ContentStateView("Loading") { CircularProgressIndicator() }
            }
            else -> {
                swipeRefreshState.isRefreshing = false
                ContentStateView("Search City or fetch all data") { }
            }
        }
    }
}


@Composable
fun ContentStateView(text: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
        Text(text = text)
    }
}

