package com.example.login001v.view.registro

import android.app.Application
import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login001v.view.theme.Tema
import com.example.login001v.viewmodel.AppViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {

    val context = LocalContext.current
    val application = context.applicationContext as Application
    val vm: RegistroViewModel = viewModel(
        factory = AppViewModelFactory(application)
    )
    val state = vm.uiState
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")


    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            // los meses en Calendar van de 0-11, pero LocalDate de 1-12!!
            vm.onFechaNacimientoChange(LocalDate.of(year, month + 1, day))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    // ----------------------------------------------------

    Tema {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crea tu Cuenta") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), //evita que el form se corte
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Únete a Mil Sabores!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Regístrate con tu correo Duoc y obtén beneficios.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = vm::onNombreChange,
                    label = { Text("Nombre Completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = vm::onEmailChange,
                    label = { Text("Correo Electrónico (Duoc)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña (mín. 6 caracteres)") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.fechaNacimiento?.format(dateFormatter) ?: "",
                    onValueChange = {},
                    label = { Text("Fecha de Nacimiento") },
                    readOnly = true, // evita que el teclado aparezca
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Filled.DateRange, "Seleccionar Fecha")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))


                // mensajes error/éxito
                if (state.error != null) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (state.success != null) {
                    Text(
                        text = state.success,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // botón registro
                Button(
                    onClick = {
                        vm.submit(onRegistroSuccess)
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text(if (state.isLoading) "Registrando..." else "Registrarse")
                }
            }
        }
    }
}