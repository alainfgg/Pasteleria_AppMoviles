package com.example.login001v.view.registro

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.database.ProductoDatabase
import com.example.login001v.data.model.Usuario
import com.example.login001v.data.repository.AuthRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

class RegistroViewModel (application: Application) : AndroidViewModel(application) {

    var uiState by mutableStateOf(RegistroUiState())
        private set
    // funciones para actualizar el estado desde la UI
    private val repo: AuthRepository = AuthRepository(
        ProductoDatabase.getDatabase(application).usuarioDao()
    )
    fun onNombreChange(value: String) {
        uiState = uiState.copy(nombre = value, error = null, success = null)
    }
    fun onEmailChange(value: String) {
        uiState = uiState.copy(email = value, error = null, success = null)
    }
    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null, success = null)
    }
    fun onFechaNacimientoChange(value: LocalDate) {
        uiState = uiState.copy(fechaNacimiento = value, error = null, success = null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submit(onSuccess: () -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null, success = null)

        // validaciones (si alguna falla, para y muestra error
        if (!validarCamposVacios()) return
        if (!validarPassword()) return


        // solo cuenta como simulación, ya que no almacena nada
        val nuevoUsuario = Usuario(
            nombre = uiState.nombre.trim(),
            email = uiState.email.trim(),
            password = uiState.password,
            fechaNacimiento = uiState.fechaNacimiento.toString() //
        )

        val edad = calcularEdad()
        val esCorreoDuoc = uiState.email.endsWith("@duoc.cl") || uiState.email.endsWith("@profesor.duoc.cl")

        var mensajeExito = "¡Registro exitoso!"
        if (edad != null && edad > 50) {
            mensajeExito += " (Descuento 50% aplicado)" // > 50 años
        }

        // Torta Duoc
        mensajeExito += " (Beneficio Torta Duoc activado)"

        uiState = uiState.copy(isLoading = false, success = mensajeExito)
        onSuccess()

        viewModelScope.launch {
            val registroOk = repo.registrar(nuevoUsuario)

            if (registroOk) {
                // Lógica de beneficios (igual que antes)
                val edad = calcularEdad()

                val esCorreoDuoc = uiState.email.endsWith("@duoc.cl") || uiState.email.endsWith("@profesor.duoc.cl")

                var mensajeExito = "¡Registro exitoso!"

                if (edad != null && edad > 50)
                {
                    mensajeExito += " (Descuento 50% aplicado)"
                }


                if (esCorreoDuoc) {
                    mensajeExito += "\n• Beneficio Torta Duoc activado 🎓"
                }
                uiState = uiState.copy(isLoading = false, success = mensajeExito)
                onSuccess()
            } else {
                uiState = uiState.copy(isLoading = false, error = "El correo ya está registrado.")
            }
        }

    }

    private fun validarCamposVacios(): Boolean {
        if (uiState.nombre.isBlank() || uiState.email.isBlank() || uiState.password.isBlank() || uiState.fechaNacimiento == null) {
            uiState = uiState.copy(error = "Por favor, completa todos los campos.", isLoading = false)
            return false
        }
        return true
    }

    private fun validarPassword(): Boolean {
        // Lógica migrada de Registro.js
        if (uiState.password.length < 6) {
            uiState = uiState.copy(
                error = "La contraseña debe tener al menos 6 caracteres.",
                isLoading = false
            )
            return false
        }
        return true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calcularEdad(): Int? {
        // > 50 años
        uiState.fechaNacimiento?.let { return Period.between(it, LocalDate.now()).years }
        return null
    }
}