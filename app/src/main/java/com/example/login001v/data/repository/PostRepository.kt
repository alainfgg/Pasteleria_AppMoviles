package com.example.login001v.data.repository

import com.example.login001v.data.model.Post
import com.example.login001v.data.remote.RetrofitInstance

class PostRepository {
    //función que obtiene los posts!! desde la api
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}