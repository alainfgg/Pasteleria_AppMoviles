package com.example.login001v.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login001v.data.dao.CartDao
import com.example.login001v.data.dao.ProductoDao
import com.example.login001v.data.dao.UsuarioDao
import com.example.login001v.data.model.CartItem
import com.example.login001v.data.model.OrderItem
import com.example.login001v.data.model.Producto
import com.example.login001v.data.model.Usuario

@Database(
    entities = [
        Usuario::class,
        Producto::class,
        CartItem::class,
        OrderItem::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mil_sabores_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}