package com.example.login001v.ui.registro

import java.time.LocalDate

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    // usoo de LocalDate para la fecha de nacimiento
    val fechaNacimiento: LocalDate? = null,
    val codigoDescuento: String = "",

    // Estado Ui
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null
)