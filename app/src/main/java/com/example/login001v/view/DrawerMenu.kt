package com.example.login001v.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.selects.select


@Composable
fun DrawerMenu(
    username:String,
    navController: NavController
){ // inicio Drawer
    Column(modifier=Modifier.fillMaxSize() )
    { // inicio columna

        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) // fin Box
        {// inicio contenido
            Text(
               text="Bienvenido, $username",
                style=MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier=Modifier
                    .align(Alignment.BottomStart)
            )
        }// termino contenido

        // Items


        LazyColumn (modifier=Modifier.weight(1f) )
        {

            // INICIO DE ITEMS DE PASTELES
            item{ // item 1
                NavigationDrawerItem(
                    label = {Text("Torta de Chocolate")},
                    selected = false,
                    onClick = {
                        val nombre= Uri.encode("Torta de Chocolate")
                        val precio="45000" // Precio del PDF
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },
                    icon = {Icon(Icons.Default.Cake , contentDescription = "Chocolate"    )}
                )
            }

            item{ // item 2
                NavigationDrawerItem(
                    label = {Text("Torta de Frutas")},
                    selected = false,
                    onClick = {
                        val nombre= Uri.encode("Torta de Frutas")
                        val precio="50000" // Precio del PDF
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },
                    icon = {Icon(Icons.Default.Icecream , contentDescription = "Frutas"    )}
                )
            }

            item{ // item 3
                NavigationDrawerItem(
                    label = {Text("Torta de Vainilla")},
                    selected = false,
                    onClick = {
                        val nombre= Uri.encode("Torta de Vainilla")
                        val precio="40000" // Precio del PDF
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },
                    icon = {Icon(Icons.Default.BakeryDining , contentDescription = "Vainilla"    )}
                )
            }

            item{ // item 4
                NavigationDrawerItem(
                    label = {Text("Torta de Manjar")},
                    selected = false,
                    onClick = {
                        val nombre= Uri.encode("Torta de Manjar")
                        val precio="42000" // Precio del PDF
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },
                    icon = {Icon(Icons.Default.Star , contentDescription = "Manjar"    )}
                )
            }

            item{ // item 5
                NavigationDrawerItem(
                    label = {Text("Mousse de Chocolate")},
                    selected = false,
                    onClick = {
                        val nombre= Uri.encode("Mousse de Chocolate")
                        val precio="5000" // Precio del PDF
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },
                    icon = {Icon(Icons.Default.LunchDining , contentDescription = "Mousse"    )}
                )
            }
            // --- FIN DE ITEMS DE PASTELES ---

            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp)) // Separador
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Preguntas Frecuentes") },
                    selected = false,
                    onClick = { navController.navigate("faq") }, // lleva a FAQ
                    icon = { Icon(Icons.Default.QuestionAnswer, "FAQ") }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Contacto") },
                    selected = false,
                    onClick = { navController.navigate("info") }, // lleva a info (contacto)
                    icon = { Icon(Icons.Default.Info, "Contacto") }
                )
            }
            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp)) // Separador
            }
            item{
                NavigationDrawerItem(
                    label = {Text("API REST")},
                    selected = false,
                    onClick = {navController.navigate("post")},
                    icon = {Icon (Icons.Default.AccessTime, "API REST")}
                )
            }

        }// fin Lazy


        //Footer
        Text(
            text="Pastelería Mil Sabores, 2025",
            style=MaterialTheme.typography.bodySmall,
            modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center


        )



    }// fin columna

}// fin DrawerMENU



@Preview(showBackground = true)
@Composable


fun DrawerMenuPreview(){
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}