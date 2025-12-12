package com.example.login001v.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.login001v.data.database.AppDatabase
import com.example.login001v.data.repository.AuthRepository
import com.example.login001v.data.repository.ProductoRepository
import com.example.login001v.view.cart.CartViewModel
import com.example.login001v.view.login.LoginViewModel
import com.example.login001v.view.registro.RegistroViewModel

class AppViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = AppDatabase.getDatabase(application)
        val authRepo = AuthRepository(db.usuarioDao())
        val prodRepo = ProductoRepository(db.productoDao())


        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepo) as T
        }
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistroViewModel(authRepo) as T
        }
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(prodRepo) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}