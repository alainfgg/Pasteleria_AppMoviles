package com.example.login001v.data.repository

import com.example.login001v.data.dao.UsuarioDao
import com.example.login001v.data.model.Usuario


class AuthRepository(
    private val usuarioDao: UsuarioDao
){
    //función de login real
    suspend fun login(usuario:String, password:String): Boolean{
        val count = usuarioDao.login(usuario, password)
        return count > 0 // Si encontró 1, es verdadero
    }

    //función QUE REGISTRA AL USUARIO
    //NO ACEPTA EMAILS DUPLICADOS ESTO
    suspend fun registrar(usuario: Usuario): Boolean {
        return try {
            // Verificamos si el email ya existe
            if (usuarioDao.emailExiste(usuario.email) > 0) {
                return false
            }
            usuarioDao.insertar(usuario)
            true
        } catch (e: Exception) {
            false
        }
    }
}