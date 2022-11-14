package com.example.exercisecomposeapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercisecomposeapi.data.model.GroupOne
import com.example.exercisecomposeapi.data.repository.ExerciseRepository
import com.example.exercisecomposeapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(private val repo: ExerciseRepository) : ViewModel() {
    val userListData = MutableLiveData<Resource<List<GroupOne>?>>(Resource.Idle())
    val search = MutableLiveData("")
    val isSearchMode = MutableLiveData(false)
    val searchSwitch = mutableListOf<Resource<List<GroupOne>?>>(Resource.Idle())

    init {
        getDetails(null)
    }

    fun change(value: String){
        isSearchMode.value = true
        search.value =value
    }

    fun searchData(){
        search.value?.let{value ->
            getDetails(value)
        }
    }

    fun searchClear(){
        search.value = ""
    }

    fun refresh(){
        if (isSearchMode.value == true) searchData() else getDetails(null)
    }

    private fun getDetails(city: String?){
        userListData.value = Resource.Loading()
        viewModelScope.launch {
            val theRepo = repo.getUsers(city)
            userListData.value = theRepo
        }
    }
}