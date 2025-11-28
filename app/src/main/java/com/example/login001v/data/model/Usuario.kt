package com.example.login001v.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date


@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["email"], unique = true)]
)

data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val fechaNacimiento: String,
)