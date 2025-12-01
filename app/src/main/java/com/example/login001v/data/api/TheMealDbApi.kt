package com.example.login001v.data.api

import com.example.login001v.data.model.PostreResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TheMealDbApi {
    // endpoint para buscar por la categoría "Postre"
    @GET("filter.php?c=Dessert")
    suspend fun obtenerPostre(): PostreResponse
}

// singleton para crear la conexión (punto de acceso)
object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val apiService: TheMealDbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMealDbApi::class.java)
    }
}