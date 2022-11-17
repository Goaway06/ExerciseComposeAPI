
package com.example.exercisecomposeapi.ui.screens

import androidx.lifecycle.*
import com.example.exercisecomposeapi.data.local.GroupOneEntity
import com.example.exercisecomposeapi.domain.repository.ExerciseRepository
import com.example.exercisecomposeapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(private val repo: ExerciseRepository) : ViewModel() {
    val userListData = MutableLiveData<Resource<List<GroupOneEntity>?>>(Resource.Idle())
    val search = MutableLiveData("")
    val isSearchMode = MutableLiveData(false)
    val searchSwitch = mutableListOf<Resource<List<GroupOneEntity>?>>(Resource.Idle())
    val sortList = (listOf<GroupOneEntity>())

    val userData = MutableLiveData<LiveData<List<GroupOneEntity>>>()

    init {
        getDetails()
    }

    fun sortData(){
        sortList.sortedBy {it.age}
    }

    fun clearFilter() {
        search.value = ""
        isSearchMode.value = false
        refresh()
    }

    fun change(value: String){
        isSearchMode.value = true
        search.value =value
    }

    fun searchData(){
        search.value?.let{value ->
            getDetails()
        }
    }

    fun searchClear(){
        search.value = ""
    }

    fun refresh(){
        if (isSearchMode.value == true) searchData() else getDetails()
    }

    private fun getDetails(){
        viewModelScope.launch {
            val theRepo = repo.getData()
            userData.value = theRepo
        }
    }
}
