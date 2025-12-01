package com.example.login001v.view.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.login001v.view.theme.Tema

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(onNavigateBack: () -> Unit) {

    Tema {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Ponte en Contacto") },
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ponte en Contacto",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "¿Tienes alguna pregunta, sugerencia o pedido especial?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(32.dp))


                InfoCard(
                    icon = Icons.Default.Email,
                    title = "Correo Electrónico",
                    content = "Escríbenos a: contacto@milsabores.cl"
                )
                InfoCard(
                    icon = Icons.Default.Phone,
                    title = "Teléfono",
                    content = "Llámanos al: +56 9 1234 5678"
                )
                InfoCard(
                    icon = Icons.Default.LocationOn,
                    title = "Ubicación",
                    content = "Calle Tu Corazón #210, Olmué, Valparaíso"
                )
            }
        }
    }
}

@Composable
private fun InfoCard(icon: ImageVector, title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}