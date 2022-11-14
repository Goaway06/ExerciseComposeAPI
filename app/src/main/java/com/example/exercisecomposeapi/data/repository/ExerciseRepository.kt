package com.example.exercisecomposeapi.data.repository

import com.example.exercisecomposeapi.data.model.GroupOne
import com.example.exercisecomposeapi.data.network.RestDataService
import com.example.exercisecomposeapi.utils.Resource
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val network: RestDataService) {

    suspend fun getUsers(cityEntry: String?): Resource<List<GroupOne>?>{
        val repo = network.getGroupData(dataFilter(cityEntry))

        if (repo.isSuccessful){
            return Resource.Success(data = repo.body())
        }

        return Resource.Failure(message = repo.message())
    }

    private fun dataFilter(cityEntry: String?): String?{
        if(cityEntry != null) {
            return "{\"CITY\": \"${cityEntry}\"}"
        }
        return null
    }
}