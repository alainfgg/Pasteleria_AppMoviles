package com.example.login001v.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precioUnitario: Int,
    val cantidad: Int,
    val imagenResId: Int,
    // Personalización
    val tamano: String,
    val forma: String,
    val mensaje: String,
    val direccion: String
) {
    val precioTotal: Int
        get() = precioUnitario * cantidad
}

@Entity(tableName = "order_history")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemsResumen: String, // Texto con el resumen de productos
    val totalPagado: Int,
    val fecha: Long = System.currentTimeMillis()
)

// DAO
@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: CartItem)

    @Update
    suspend fun update(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM order_history ORDER BY fecha DESC")
    fun getOrderHistory(): Flow<List<OrderItem>>

    @Insert
    suspend fun insertOrder(order: OrderItem)

}