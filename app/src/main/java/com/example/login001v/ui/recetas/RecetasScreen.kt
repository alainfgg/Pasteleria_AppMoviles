package com.example.login001v.ui.recetas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage //Coil
import com.example.login001v.viewmodel.RecetasViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable

@Composable
fun RecetasScreen(
    viewModel: RecetasViewModel = viewModel()
) {
    val postres = viewModel.listaPostres
    val loading = viewModel.isLoading
    val error = viewModel.errorMsg

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "🍰 Ideas de Postres (API)",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
            Button(onClick = { viewModel.cargarRecetas() }) {
                Text("Reintentar")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(postres) { postre ->
                if (postre != null) {
                    //obtiene contexto para el mensaje
                    val context = LocalContext.current

                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .clickable {
                            // Acción al hacer click: Mostrar mensaje
                            Toast.makeText(context, "Elegiste: ${postre.name} 🍰", Toast.LENGTH_SHORT).show()
                        }
                    ){
                    Column {
                        AsyncImage(
                            model = postre.imageUrl,
                            contentDescription = postre.name,
                            modifier = Modifier.height(120.dp).fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = postre.name ?: "Sin nombre",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(8.dp),
                            maxLines = 2
                        )
                        }
                    }
                }
            }
        }
    }
}