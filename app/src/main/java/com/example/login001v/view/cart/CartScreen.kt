package com.example.login001v.view.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login001v.data.model.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = viewModel()
) {
    val items by viewModel.cartItems.collectAsState()
    val subtotal by viewModel.totalCart.collectAsState()
    val context = LocalContext.current

    var showBoletaDialog by remember { mutableStateOf(false) }
    var boletaContent by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            if (items.isNotEmpty()) {
                Surface(
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // Input para cupon de descuento
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = viewModel.discountCode,
                                onValueChange = { viewModel.discountCode = it },
                                label = { Text("Código Descuento") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            Button(onClick = { viewModel.applyDiscount() }) {
                                Text("Aplicar")
                            }
                        }
                        if (viewModel.isDiscountApplied) {
                            Text("¡Descuento del 50% aplicado!", color = Color.Black, style = MaterialTheme.typography.bodySmall)
                        }
                        Divider(modifier = Modifier.padding(vertical = 8.dp))


                        val discountAmount = if (viewModel.isDiscountApplied) (subtotal * 0.50).toInt() else 0
                        val totalFinal = subtotal - discountAmount

                        // Muestra el subtotal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal:", style = MaterialTheme.typography.bodyLarge)
                            Text("$$subtotal", style = MaterialTheme.typography.bodyLarge)
                        }

                        // Muestra en caso de que se aplica un descuento
                        if (viewModel.isDiscountApplied) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Descuento:", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                                Text("-$$discountAmount", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        // Muestra el total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", style = MaterialTheme.typography.titleLarge)
                            Text("$${totalFinal}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))


                        Button(
                            onClick = {
                                viewModel.confirmarCompra { textoBoleta ->
                                    boletaContent = textoBoleta
                                    showBoletaDialog = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.ShoppingCartCheckout, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Confirmar Compra")
                        }
                        // -----------------------------------------------
                    }
                }
            }
        }
    ) { innerPadding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío 😔", style = MaterialTheme.typography.headlineSmall)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    CartItemCard(item, viewModel)
                }
            }
        }
    }

    // Boleta
    if (showBoletaDialog) {
        AlertDialog(
            onDismissRequest = { showBoletaDialog = false },
            title = { Text("Comprobante de Pago") },
            text = {
                Column {
                    Text(
                        text = boletaContent,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showBoletaDialog = false
                    // Opcional: para navegar al historial automatico
                     navController.navigate("order_history")
                }) {
                    Text("Cerrar")
                }
            },
            icon = { Icon(Icons.Default.ReceiptLong, null) }
        )
    }
}

@Composable
fun CartItemCard(item: CartItem, viewModel: CartViewModel) {
    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imagenResId),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(item.nombre, fontWeight = FontWeight.Bold)
                Text("Precio: $${item.precioUnitario}", style = MaterialTheme.typography.bodySmall)
                if(item.tamano.isNotEmpty()) Text("Tam: ${item.tamano}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                if(item.forma.isNotEmpty()) Text("Forma: ${item.forma}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.updateQuantity(item, item.cantidad - 1) }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Remove, contentDescription = "Menos")
                    }
                    Text("${item.cantidad}", modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = { viewModel.updateQuantity(item, item.cantidad + 1) }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "Más")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("$${item.precioTotal}", fontWeight = FontWeight.Bold)
                IconButton(onClick = { viewModel.removeFromCart(item) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}