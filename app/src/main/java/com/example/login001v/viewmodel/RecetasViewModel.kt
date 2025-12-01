package com.example.login001v.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.api.RetrofitClient
import com.example.login001v.data.model.Postre
import kotlinx.coroutines.launch

class RecetasViewModel : ViewModel() {

    // estadis de la UI
    var listaPostres by mutableStateOf<List<Postre?>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMsg by mutableStateOf<String?>(null)

    init {
        cargarRecetas()
    }

    fun cargarRecetas() {
        viewModelScope.launch {
            isLoading = true
            errorMsg = null
            Log.d("API_TEST", "Iniciando llamada a la API...")
            try {
                val response = RetrofitClient.apiService.obtenerPostre()
                Log.d("API_TEST", "Respuesta recibida: $response")
                listaPostres = response.meals ?: emptyList()
                Log.d("API_TEST", "Tamaño de la lista: ${listaPostres.size}")
            } catch (e: Exception) {
                Log.e("API_TEST", "Error FATAL: ${e.message}")
                e.printStackTrace()
                errorMsg = "Error al cargar recetas: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}