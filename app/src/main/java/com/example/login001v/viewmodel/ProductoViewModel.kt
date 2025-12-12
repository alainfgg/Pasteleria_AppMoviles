package com.example.login001v.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.database.AppDatabase
import com.example.login001v.data.model.Producto
import com.example.login001v.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.obtenerPedidos().collect { lista ->
                _productos.value = lista
            }
        }
    }
    fun guardarProducto(producto: Producto) {

        // TRUCO VISUAL (Optimistic Update)
        _productos.value = listOf(producto) + _productos.value

        // PERSISTENCIA REAL
        viewModelScope.launch {
            repository.guardarPedido(producto)
        }
    }

}