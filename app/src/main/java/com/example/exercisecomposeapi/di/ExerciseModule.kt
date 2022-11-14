package com.example.exercisecomposeapi.di

import android.content.Context
import com.example.exercisecomposeapi.data.network.RestDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class ExerciseModule {
    @Provides
    fun providesRetrofit(@ApplicationContext context: Context): RestDataService{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return Retrofit
            .Builder()
            .baseUrl("https://exercise-646d.restdb.io/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().cache(Cache(context.cacheDir, 10 * 1024 * 1024))
                    .addInterceptor{chain ->
                        val requestData = chain.request().newBuilder()
                        .addHeader("x-apikey", "5c5c7076f210985199db5488")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Content-Type", "application/json")
                            .build()
                        chain.proceed(requestData)
                    }.addInterceptor(logging).build()
            )
            .build()
            .create(RestDataService::class.java)
    }
}