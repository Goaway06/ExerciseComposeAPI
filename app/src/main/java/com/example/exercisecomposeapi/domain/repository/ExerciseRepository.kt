package com.example.exercisecomposeapi.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.exercisecomposeapi.data.local.AppDatabase
import com.example.exercisecomposeapi.data.local.GroupOneEntity
import com.example.exercisecomposeapi.domain.model.GroupOne
import com.example.exercisecomposeapi.data.network.RestDataService
import com.example.exercisecomposeapi.utils.Resource
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val network: RestDataService, private val db: AppDatabase):
    Repository {

    /*suspend fun getUsers(cityEntry: String?): Resource<List<GroupOneEntity>?>{
        val repo = network.getGroupData(dataFilter(cityEntry))

        if (repo.isSuccessful){
            return Resource.Success(data = repo.body())
        }

        return Resource.Failure(message = repo.message())
    }*/

    private fun dataFilter(cityEntry: String?): String?{
        if(cityEntry != null) {
            return "{\"CITY\": \"${cityEntry}\"}"
        }
        return null
    }

    override suspend fun insertAll(repo: List<GroupOneEntity>) {
        db.groupOneDao().insertAll(repo)
    }

    override fun getData():LiveData<List<GroupOneEntity>> {
        val flow = flow<List<GroupOneEntity>> {
            val data = db.groupOneDao().getData().asFlow().first().isEmpty()
            if (data){
                try {
                    val fetch: List<GroupOneEntity> = network.getUserData()
                    db.groupOneDao().insertAll(fetch)
                    emit(db.groupOneDao().getData().asFlow().first())
                }catch (e: Exception){
                    Log.d("TAG", "getData" + e)
                }

            }else{
                emit(db.groupOneDao().getData().asFlow().first())
            }
        }
        return flow.asLiveData()
    }
}