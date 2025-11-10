package com.example.login001v.data.model

//ESTA CLASE PERMITE UN POST OBTENIDO DESDE LA API

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)