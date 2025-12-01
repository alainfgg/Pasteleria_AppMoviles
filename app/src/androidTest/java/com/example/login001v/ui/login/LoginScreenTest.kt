package com.example.login001v.ui.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verificarElementosExisten() {
        // carga la pantalla
        composeTestRule.setContent {
            // pasamos viewModels falsos o dejamos que se creen
            LoginScreen(
                onLoginSuccess = {},
                onNavigateToRegistro = {}
            )
        }

        // verificamos que el título "Bienvenido" en la screen exista
        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()

        // verificamos que el botón de registro exista
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate aquí").assertIsDisplayed()
    }

    @Test
    fun verificarBotonLoginExiste() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = {},
                onNavigateToRegistro = {}
            )
        }

        // buscamos el botón por su texto inicial
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()
    }

}