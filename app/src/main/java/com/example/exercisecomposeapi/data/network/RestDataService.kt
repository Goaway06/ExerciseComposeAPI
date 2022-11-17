package com.example.exercisecomposeapi.data.network

import com.example.exercisecomposeapi.data.local.GroupOneEntity
import com.example.exercisecomposeapi.domain.model.GroupOne
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestDataService {
    @GET("group-1")
    // suspend fun getGroupData(@Query("q") cityFilter: String?):Response<List<GroupOneEntity>>

    suspend fun getUserData(): List<GroupOneEntity>
}