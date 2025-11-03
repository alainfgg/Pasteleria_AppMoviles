package com.example.login001v.ui.registro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.Period

class RegistroViewModel : ViewModel() {

    var uiState by mutableStateOf(RegistroUiState())
        private set

    // funciones para actualizar el estado desde la UI

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

    fun onCodigoDescuentoChange(value: String) {
        uiState = uiState.copy(codigoDescuento = value, error = null, success = null)
    }


    fun submit(onSuccess: () -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null, success = null)

        // validaciones (si alguna falla, para y muestra error
        if (!validarCamposVacios()) return
        if (!validarPassword()) return
        if (!validarEmailDuoc()) return

        // solo cuenta como simulación, ya que no almacena nada

        val edad = calcularEdad()
        val usaCodigoFelices50 = uiState.codigoDescuento.uppercase() == "FELICES50"

        var mensajeExito = "¡Registro exitoso!"
        if (edad != null && edad > 50) {
            mensajeExito += " (Descuento 50% aplicado)" // > 50 años
        }
        if (usaCodigoFelices50) {
            mensajeExito += " (Descuento 10% aplicado)" // cod desc "FELICES50"
        }
        // Torta Duoc
        mensajeExito += " (Beneficio Torta Duoc activado)"

        uiState = uiState.copy(isLoading = false, success = mensajeExito)
        onSuccess()
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

    private fun validarEmailDuoc(): Boolean {
        val email = uiState.email.trim()
        if (!email.endsWith("@duoc.cl") && !email.endsWith("@profesor.duoc.cl")) {
            uiState = uiState.copy(
                error = "Debes usar un correo @duoc.cl o @profesor.duoc.cl.",
                isLoading = false
            )
            return false
        }
        return true
    }

    private fun calcularEdad(): Int? {
        // > 50 años
        uiState.fechaNacimiento?.let {
            return Period.between(it, LocalDate.now()).years
        }
        return null
    }
}