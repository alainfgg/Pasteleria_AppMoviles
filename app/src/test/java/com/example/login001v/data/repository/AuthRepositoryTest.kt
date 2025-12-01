package com.example.login001v.data.repository

import com.example.login001v.data.dao.UsuarioDao
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.Assert.*

class AuthRepositoryTest {

    private val mockUsuarioDao = mockk<UsuarioDao>()
    private val repository = AuthRepository(mockUsuarioDao)

    @Test
    fun `login devuelve true cuando las credenciales son correctas`() = runBlocking {
        // PREPARACIÓN: Le decimos al Mock que devuelva 1 (encontrado) cuando le pregunten
        coEvery { mockUsuarioDao.login("admin@duoc.cl", "123456") } returns 1

        // EJECUCIÓN
        val resultado = repository.login("admin@duoc.cl", "123456")

        // VERIFICACIÓN
        assertTrue(resultado)
    }
    @Test
    fun `login devuelve false cuando las credenciales son incorrectas`() = runBlocking {
        // PREPARACIÓN: Le decimos al Mock que devuelva 0 (no encontrado)
        coEvery { mockUsuarioDao.login("malo@duoc.cl", "malapass") } returns 0

        // EJECUCIÓN
        val resultado = repository.login("malo@duoc.cl", "malapass")

        // VERIFICACIÓN
        assertFalse(resultado) // Esperamos que sea falso
    }
}