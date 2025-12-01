package com.example.login001v.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.AccessTime // (Necesaria si se usa)
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionAnswer // (Necesaria si se usa)
import androidx.compose.material.icons.filled.BakeryDining // <- Asegúrate de que esta y las siguientes estén importadas
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Icecream // <-
import androidx.compose.material.icons.filled.LunchDining // <-
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.ui.home.HomeScreen
import com.example.login001v.ui.theme.Tema
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.ShoppingCart //PARA CARRITO
import androidx.compose.material.icons.filled.Receipt // PARA HISTORIAL DE PEDIDOS (NUEVO)

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Tema {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    // --- CABECERA ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Hola, $username",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    // --- LISTA DE OPCIONES ---
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        // SECCIÓN 1: PASTELES
                        item { DrawerSectionTitle("🍰 Pasteles y Tortas") }

                        item {
                            DrawerItem(Icons.Default.Cake, "Torta de Chocolate") {
                                navigateToProduct("Torta Cuadrada de Chocolate", "45000", navController, scope, drawerState)
                            }
                        }
                        item {
                            // Se usa Icecream si está importado, o Star como sustituto
                            DrawerItem(Icons.Default.Icecream, "Torta de Frutas") {
                                navigateToProduct("Torta Cuadrada de Frutas", "50000", navController, scope, drawerState)
                            }
                        }
                        item {
                            // Se usa BakeryDining si está importado, o Favorite como sustituto
                            DrawerItem(Icons.Default.BakeryDining, "Torta de Vainilla") {
                                navigateToProduct("Torta Circular de Vainilla", "40000", navController, scope, drawerState)
                            }
                        }
                        item {
                            DrawerItem(Icons.Default.Star, "Torta de Manjar") {
                                navigateToProduct("Torta Circular de Manjar", "42000", navController, scope, drawerState)
                            }
                        }

                        // SECCIÓN 2: POSTRES
                        item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
                        item { DrawerSectionTitle("🍮 Postres Individuales") }

                        item {
                            // Se usa LunchDining si está importado, o ShoppingCart como sustituto
                            DrawerItem(Icons.Default.LunchDining, "Mousse de Chocolate") {
                                navigateToProduct("Mousse de Chocolate", "5000", navController, scope, drawerState)
                            }
                        }
                        item {
                            DrawerItem(Icons.Default.LunchDining, "Tiramisú Clásico") {
                                navigateToProduct("Tiramisú Clásico", "5500", navController, scope, drawerState)
                            }
                        }

                        // SECCIÓN 3: CAFETERÍA
                        item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
                        item { DrawerSectionTitle("☕ Cafetería") }

                        item {
                            DrawerItem(Icons.Default.Home, "Empanada de Manzana") {
                                navigateToProduct("Empanada de Manzana", "3000", navController, scope, drawerState)
                            }
                        }
                        item {
                            DrawerItem(Icons.Default.Home, "Café del Día") {
                                navigateToProduct("Café del Día", "2500", navController, scope, drawerState)
                            }
                        }

                        // SECCIÓN 4: CONFIGURACIÓN/AYUDA
                        item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
                        item { DrawerSectionTitle("⚙️ Configuración") }

                        item {
                            DrawerItem(Icons.Default.Info, "Preguntas Frecuentes") {
                                scope.launch { drawerState.close() }
                                navController.navigate("faq")
                            }
                        }
                        item {
                            DrawerItem(Icons.Default.Phone, "Contacto") {
                                scope.launch { drawerState.close() }
                                navController.navigate("info")
                            }
                        }
                        // SECCIÓN 5: CARRITO DE COMPRAS
                        item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
                        item { DrawerSectionTitle("🛒 Compras") }

                        item {
                            DrawerItem(Icons.Default.ShoppingCart, "Ver Carrito") {
                                scope.launch { drawerState.close() }
                                navController.navigate("cart")
                            }
                        }

                        // --- NUEVO: PEDIDOS REALIZADOS ---
                        item {
                            DrawerItem(Icons.Default.Receipt, "Pedidos Realizados") {
                                scope.launch { drawerState.close() }
                                navController.navigate("order_history")
                            }
                        }
                        // ---------------------------------

                        // Nuevo elemento de API REST
                        item {
                            DrawerItem(Icons.Default.AccessTime, "API REST") {
                                scope.launch { drawerState.close() }
                                navController.navigate("post")
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            NavigationDrawerItem(
                                label = { Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error) },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                icon = { Icon(Icons.Default.ExitToApp, null, tint = MaterialTheme.colorScheme.error) },
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }

                        item { Spacer(modifier = Modifier.height(24.dp)) }
                    }

                    // --- FOOTER ---
                    // Agregado el footer de la versión duplicada
                    Text(
                        text="Pastelería Mil Sabores, 2025",
                        style=MaterialTheme.typography.bodySmall,
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        ) {
            // LLAMADA A HOME (FONDO)
            HomeScreen(
                username = username,
                navController = navController,
                onOpenDrawer = { scope.launch { drawerState.open() } }
            )
        }
    }
}


// --- FUNCIONES AUXILIARES ---

// La anotación @Preview estaba aquí en el código original, pero se suele omitir en producción
@Composable
fun DrawerSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun DrawerItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = false,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
    )
}

fun navigateToProduct(
    nombre: String,
    precio: String,
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    scope.launch { drawerState.close() }
    val nombreEnc = Uri.encode(nombre)
    navController.navigate("ProductoFormScreen/$nombreEnc/$precio")
}