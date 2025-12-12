package com.example.login001v.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.login001v.data.model.CartItem
import com.example.login001v.data.model.OrderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Update
    suspend fun update(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    // HISTORIAL
    @Query("SELECT * FROM order_history ORDER BY fecha DESC")
    fun getOrderHistory(): Flow<List<OrderItem>>

    @Insert
    suspend fun insertOrder(order: OrderItem)
}

