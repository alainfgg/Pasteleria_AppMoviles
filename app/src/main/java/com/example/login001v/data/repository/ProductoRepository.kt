package com.example.login001v.data.repository

import com.example.login001v.data.dao.ProductoDao
import com.example.login001v.data.model.Producto
import kotlinx.coroutines.flow.Flow

// se crea una capa intermedia entre datos y la UI


class ProductoRepository (private val productoDao: ProductoDao){

    fun obtenerPedidos(): Flow<List<Producto>> {
        return productoDao.obtenerProductos()
    }

    suspend fun obtenerPedidosSimple(): List<Producto>{
        return productoDao.obtenerTodoSimple()
    }
    suspend fun guardarPedido(producto: Producto) {
        productoDao.insertarProducto(producto)
    }

}