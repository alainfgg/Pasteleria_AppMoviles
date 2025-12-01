package com.example.login001v.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login001v.data.dao.ProductoDao
import com.example.login001v.data.dao.UsuarioDao
import com.example.login001v.data.model.Producto
import com.example.login001v.data.model.Usuario


@Database(
    entities = [Producto::class, Usuario::class],
    version=2, //nueva versión
    exportSchema = false
)

abstract class ProductoDatabase: RoomDatabase(){
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object{
        @Volatile
        private var INSTANCE: ProductoDatabase? =null

        fun getDatabase(context: Context): ProductoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_database"
                )
                    .fallbackToDestructiveMigration()
                    .build() // builder
                    INSTANCE = instance
                    instance
            } // fin return
        }//fin fun
    }// fin companion
}// fin abstract