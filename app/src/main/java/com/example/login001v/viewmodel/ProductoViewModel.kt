package com.example.login001v.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.database.ProductoDatabase

import com.example.login001v.data.model.Producto
import com.example.login001v.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application){

    private val repository : ProductoRepository
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        val dao = ProductoDatabase.getDatabase(application).productoDao()
        repository = ProductoRepository(dao)

        viewModelScope.launch {

                // cada vez que la base de datos cambie (INSERT),
                // actualizamos nuestro state flow privado _productos.

                _productos.value = repository.obtenerPedidosSimple()
            }
        }

    fun guardarProducto(producto: Producto){

        _productos.value = _productos.value + producto

        viewModelScope.launch {
            repository.guardarPedido(producto)

        }// fin Scope
    }// fin guardar
}