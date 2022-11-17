package com.example.exercisecomposeapi.domain.model

import com.google.gson.annotations.SerializedName

data class GroupOne(
    @SerializedName("_id") val id: String,
    @SerializedName("NAME")val name: String,
    @SerializedName("SURNAME")val surname: String,
    @SerializedName("AGE")val age: Int,
    @SerializedName("CITY")val city: String,
    @SerializedName("PARENTID")val parentId: String,
)
