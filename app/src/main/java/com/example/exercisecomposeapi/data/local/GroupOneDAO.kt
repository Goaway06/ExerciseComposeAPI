package com.example.exercisecomposeapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


@Dao
interface GroupOneDAO {

    @Query("SELECT * FROM GroupOneEntity")
    fun getData(): LiveData<List<GroupOneEntity>>

    @Insert
    suspend fun insertAll(repo: List<GroupOneEntity>)
}