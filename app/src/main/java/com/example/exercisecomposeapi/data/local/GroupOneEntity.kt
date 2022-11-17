package com.example.exercisecomposeapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class GroupOneEntity (
    @PrimaryKey
    @SerializedName("_id") val id: String,
    @SerializedName("NAME")val name: String,
    @SerializedName("SURNAME")val surname: String,
    @SerializedName("AGE")val age: Int,
    @SerializedName("CITY")val city: String,
    @SerializedName("PARENTID")val parentId: String,
)