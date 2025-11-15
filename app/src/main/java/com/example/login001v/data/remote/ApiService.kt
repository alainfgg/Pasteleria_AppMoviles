package com.example.login001v.data.remote

import com.example.login001v.data.model.Post
import retrofit2.http.GET

interface ApiService {
    //DEFINE SOL GET AL ENDPOINT /posts
    @GET("/post")
    suspend fun getPosts(): List<Post>
}