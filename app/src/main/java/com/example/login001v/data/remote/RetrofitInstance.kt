package com.example.login001v.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //instancia el servicio de la api una sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com") //url base de la api
            .addConverterFactory(GsonConverterFactory.create()) //conversor JSON
            .build()
            .create(ApiService::class.java) //implementa interfaz ApiService
    }
}