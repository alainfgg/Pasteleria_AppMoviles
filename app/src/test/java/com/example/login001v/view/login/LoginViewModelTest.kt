package com.example.login001v.view.login

import android.app.Application
import com.example.login001v.data.dao.UsuarioDao
import com.example.login001v.data.database.ProductoDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    //mock de la app
    private val application = mockk<Application>(relaxed = true)
    private val database = mockk<ProductoDatabase>(relaxed = true)
    private val dao = mockk<UsuarioDao>(relaxed = true)

    @Before
    fun setup() {
        every {application.applicationContext} returns application
        mockkObject(ProductoDatabase.Companion)
        every { ProductoDatabase.getDatabase(any()) } returns database
        every { database.usuarioDao() } returns dao
    }
    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `onUsernameChange actualiza el estado correctamente`() {
        // instancia el viewmodel
        val viewModel = LoginViewModel(application)
        // ejecuta la acción
        val nuevoEmail = "test@duoc.cl"
        viewModel.onUsernameChange(nuevoEmail)
        // verifica que el estado cambió
        assertEquals(nuevoEmail, viewModel.uiState.username)
        assertEquals(null, viewModel.uiState.error)
    }


}