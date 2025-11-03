package com.example.login001v.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName="productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val nombre: String,
    val precio:String,
    val cantidad:String,
    val direccion: String,

    val tamano: String,
    val forma: String,
    val mensaje: String
)
{
    // ESTO ES PORQUE A LA ANIMACIÓN, DENTRO DEL LAZYCOLUMN,
    // LE OTORGAMOS DOBLE KEY EN id = 0, ASÍ QUE NO INICIALIZA
    // NADA DE ANIMACIONES DENTRO DE LA MISMA COLUMNA :).

    @Ignore
    val uuid: String = UUID.randomUUID().toString()
}
