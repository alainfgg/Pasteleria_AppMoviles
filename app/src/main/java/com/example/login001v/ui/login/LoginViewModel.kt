package com.example.login001v.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.login001v.data.repository.AuthRepository

class LoginViewModel (
    private val repo: AuthRepository = AuthRepository()
): ViewModel(){
    var uiState by mutableStateOf(LoginUiState() )

    fun onUsernameChange(value:String){
        // El error se limpia al escribir
        uiState = uiState.copy(username = value, error = null)
    }

    fun onPasswordChange(value:String){
        // El error se limpia al escribir
        uiState = uiState.copy(password = value, error = null)
    }
    fun submit(onSucces:(String) -> Unit){

        uiState = uiState.copy(isLoading = true, error = null)

        // validación
        if (uiState.password.length < 6) {
            uiState = uiState.copy(
                error = "La contraseña debe tener al menos 6 caracteres.",
                isLoading = false
            )
            return // Detiene la ejecución
        }
       //fin validación

        val oK = repo.login(uiState.username.trim(), uiState.password)


        if(oK) {
            onSucces(uiState.username.trim())
        } else {
            uiState = uiState.copy(
                error = "Credenciales inválidas",
                isLoading = false
            )
        }
    }
}