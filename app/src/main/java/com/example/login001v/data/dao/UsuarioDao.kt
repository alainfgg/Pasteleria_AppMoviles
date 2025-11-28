package com.example.login001v.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.login001v.data.model.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(usuario: Usuario)

    @Query ("SELECT COUNT(1) FROM usuarios WHERE email =:email AND password = :password")
    suspend fun login(email: String, password: String): Int
    // LÓGICA
    //REVISA EMAIL.
    //SI RETORNA 1 = EXISTE / 0 = NO EXISTE

    @Query("SELECT COUNT (1) FROM usuarios WHERE email =:email")
    suspend fun emailExiste(email: String): Int
}