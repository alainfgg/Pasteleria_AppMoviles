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

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductoRepository

    // Estado privado (mutable)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    // Estado público (inmutable) que observa la UI
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        // Inicializamos la BBDD y el Repo
        val dao = ProductoDatabase.getDatabase(application).productoDao()
        repository = ProductoRepository(dao)

        // Cargamos la lista inicial desde la base de datos al abrir la pantalla
        viewModelScope.launch {
            _productos.value = repository.obtenerPedidosSimple()
        }
    }

    fun guardarProducto(producto: Producto) {

        // 1. TRUCO VISUAL (Optimistic Update):
        // Añadimos el nuevo producto al INICIO de la lista en memoria inmediatamente.
        // Esto hace que la UI reaccione y anime al instante, sin esperar a la base de datos.
        _productos.value = listOf(producto) + _productos.value

        // 2. PERSISTENCIA REAL:
        // Guardamos en Room en segundo plano para que no se pierda al cerrar la app.
        viewModelScope.launch {
            repository.guardarPedido(producto)
        }
    }
}