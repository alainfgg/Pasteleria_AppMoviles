package com.example.login001v.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
                            // Si no tienes Icecream, usa Star
                            DrawerItem(Icons.Default.Star, "Torta de Frutas") {
                                navigateToProduct("Torta Cuadrada de Frutas", "50000", navController, scope, drawerState)
                            }
                        }
                        item {
                            // Si no tienes BakeryDining, usa Favorite
                            DrawerItem(Icons.Default.Favorite, "Torta de Vainilla") {
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
                            // Si no tienes LunchDining, usa ShoppingCart
                            DrawerItem(Icons.Default.ShoppingCart, "Mousse de Chocolate") {
                                navigateToProduct("Mousse de Chocolate", "5000", navController, scope, drawerState)
                            }
                        }
                        item {
                            DrawerItem(Icons.Default.ShoppingCart, "Tiramisú Clásico") {
                                navigateToProduct("Tiramisú Clásico", "5500", navController, scope, drawerState)
                            }
                        }

                        // SECCIÓN 3: CAFETERÍA
                        item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
                        item { DrawerSectionTitle("☕ Cafetería") }

                        item {
                            // Usamos iconos genéricos para asegurar que compile
                            DrawerItem(Icons.Default.Home, "Empanada de Manzana") {
                                navigateToProduct("Empanada de Manzana", "3000", navController, scope, drawerState)
                            }
                        }
                        item {
                            DrawerItem(Icons.Default.Home, "Café del Día") {
                                navigateToProduct("Café del Día", "2500", navController, scope, drawerState)
                            }
                        }

                        // SECCIÓN 4: AYUDA
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

// --- FUNCIONES AUXILIARES (¡NO LAS BORRES!) ---

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