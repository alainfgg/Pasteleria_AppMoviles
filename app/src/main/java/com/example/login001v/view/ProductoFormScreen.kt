package com.example.login001v.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box


import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import android.content.Intent
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login001v.data.model.Producto
import com.example.login001v.viewmodel.ProductoViewModel
import com.example.login001v.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi:: class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre:String,
    precio:String
    )
    {
    val context = LocalContext.current
    var cantidad by remember{ mutableStateOf(TextFieldValue("")) }
    var direccion by remember{ mutableStateOf(TextFieldValue("")) }

    // implementación de combobox
    val tamanoOptions = listOf("15 personas", "20 personas", "30 personas", "40 personas")
    val formaOptions = listOf("Circular", "Cuadrada")

    var expandedTamano by remember { mutableStateOf(false) }
    var selectedTamano by remember { mutableStateOf("") } // guarda tamaño de la torta


    var expandedForma by remember { mutableStateOf(false) }
    var selectedForma by remember { mutableStateOf("") } //guarda circular o cuadrada


    var mensaje by remember { mutableStateOf(TextFieldValue("")) }
    //fin estados


// conectamos al viewmodel
    val viewModel: ProductoViewModel =viewModel()

    // observa directamente los productos
    val productos : List<Producto> by viewModel.productos.collectAsState()

    Scaffold (
        bottomBar = {
            BottomAppBar {
            } // fin Bootom App
        }// fin bottom

    ) // fin Scaffold

    {// inicio inner
            innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally
        )// fin Column
        { // inicio contenido
            item {
                val imageId = when (nombre) {

                    "Torta de Chocolate" -> R.drawable.chocolate
                    "Torta de Frutas" -> R.drawable.frutas
                    "Torta de Vainilla" -> R.drawable.vainilla
                    "Torta de Manjar" -> R.drawable.manjar
                    "Mousse de Chocolate" -> R.drawable.mousse
                    // Imagen por defecto si no coincide
                    else -> R.drawable.logo
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Precio Base: $precio", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
        }
            item {
                //inicio nativo
                OutlinedButton(
                    onClick = {
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, "¡Oye! Mira esta $nombre que venden en Mil Sabores a $precio 🍰😋.")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, "Compartir Torta")
                        context.startActivity(shareIntent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compartir")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }//fin nativo


            // CAMPOS DE PERSONALIZACIÓN
        item{
            // CBX tamaño
            ExposedDropdownMenuBox(
                expanded = expandedTamano,
                onExpandedChange = { expandedTamano = !expandedTamano },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = selectedTamano,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tamaño") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTamano) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedTamano,
                    onDismissRequest = { expandedTamano = false }
                ) {
                    tamanoOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedTamano = option
                                expandedTamano = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier =Modifier.height(16.dp))
        }
            item {
                // CBX forma
                ExposedDropdownMenuBox(
                    expanded = expandedForma,
                    onExpandedChange = { expandedForma = !expandedForma },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = selectedForma,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Forma") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedForma) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedForma,
                        onDismissRequest = { expandedForma = false }
                    ) {
                        formaOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedForma = option
                                    expandedForma = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // mensaje
                OutlinedTextField(
                    value = mensaje,
                    onValueChange = { mensaje = it },
                    label = { Text("Mensaje Personalizado (Opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // FIN PERSONALIZACIÓN

                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Direccion") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = {
                        val producto = Producto(
                            nombre = nombre,
                            precio = precio,
                            cantidad = cantidad.text,
                            direccion = direccion.text,

                            tamano = selectedTamano,
                            forma = selectedForma,
                            mensaje = mensaje.text
                        )
                        viewModel.guardarProducto(producto)
                    },
                    // verificación de los datos
                    enabled = cantidad.text.isNotBlank() &&
                            direccion.text.isNotBlank() &&
                            selectedTamano.isNotBlank() &&
                            selectedForma.isNotBlank()
                ) // fin Button
                {
                    Text("Confirmar Pedido")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // mostrar Productos guardados
                Text("Pedidos realizados :", style = MaterialTheme.typography.headlineSmall)

                //  INICIO DE PRUEBA
                // este texto nos dirá si la lista cambia
                Text(
                    text = "DEBUG: Total de pedidos en memoria: ${productos.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
                // FIN DE PRUEBA
            }

            if (productos.isNotEmpty()){
                // de it.id a it.uuid
                    items(productos, key = {producto -> producto.uuid}) { producto ->

                            Card(
                                modifier = Modifier
                                    .animateItem()
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp, top = 8.dp)

                            )// fin Card
                            {// contenido Card
                                Column(modifier = Modifier.padding(8.dp))
                                { // contenido columna
                                    Text(
                                        text = "${producto.nombre} - ${producto.precio}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Cantidad: ${producto.cantidad}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Direccion: ${producto.direccion}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        text = "Personalización: ${producto.tamano}, ${producto.forma}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold // Resaltado
                                    )
                                    if (producto.mensaje.isNotBlank()) {
                                        Text(
                                            text = "Mensaje: ${producto.mensaje}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                } // fin contenido columna
                            }// fin contenido card
                    }// fin items
                } // fin lazy
            }// end if
            else{
                item {
                    Text(
                        text = "No hay pedidos realizados",
                        modifier = Modifier.padding(vertical = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } // fin else
    } //Fin Contenido
} // fin inner



@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Producto Ejemplo",
        precio = "$10.00"
    )
}