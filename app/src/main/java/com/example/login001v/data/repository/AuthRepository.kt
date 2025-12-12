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
        // Verificamos si ya existe el email
        val exists = usuarioDao.emailExiste(usuario.email)
        if (exists > 0) return false

        // Si no existe, insertamos
        usuarioDao.insertar(usuario)
        return true
    }
}