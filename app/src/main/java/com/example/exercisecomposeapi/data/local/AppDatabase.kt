package com.example.exercisecomposeapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [GroupOneEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun groupOneDao(): GroupOneDAO

}