package com.example.login001v.data.model

import com.google.gson.annotations.SerializedName

data class PostreResponse (
    @SerializedName("meals") val meals: List<Postre?>
)
data class Postre(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val imageUrl: String
)