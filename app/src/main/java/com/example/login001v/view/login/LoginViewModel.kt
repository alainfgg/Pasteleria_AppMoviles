package com.example.login001v.view.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.database.ProductoDatabase
import com.example.login001v.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: AuthRepository = AuthRepository(
        ProductoDatabase.getDatabase(application).usuarioDao()
    )

    var uiState by mutableStateOf(LoginUiState())

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, error = null)
    }
    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    fun submit(onSuccess: (String) -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null)

        // validación contraseña
        if (uiState.password.length < 6) {
            uiState = uiState.copy(
                error = "La contraseña debe tener al menos 6 caracteres.",
                isLoading = false
            )
            return
        }

        // llamada asíncrona a la bd
        viewModelScope.launch {
            try {
                val loginExitoso = repo.login(uiState.username.trim(), uiState.password)

                if (loginExitoso) {
                    onSuccess(uiState.username.trim())
                } else {
                    uiState = uiState.copy(
                        error = "Credenciales inválidas",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = "Error al intentar entrar: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}