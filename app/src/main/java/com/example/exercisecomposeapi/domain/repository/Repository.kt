package com.example.exercisecomposeapi.domain.repository

import androidx.lifecycle.LiveData
import com.example.exercisecomposeapi.data.local.GroupOneEntity
import com.example.exercisecomposeapi.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
    suspend fun insertAll(repo: List<GroupOneEntity>)

    fun getData(): LiveData<List<GroupOneEntity>>
}