package com.example.login001v.view.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.login001v.data.database.CartDatabase
import com.example.login001v.data.model.CartItem
import com.example.login001v.data.model.OrderItem

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CartDatabase.getDatabase(application).cartDao()

    // Carrito en tiempo real
    val cartItems: StateFlow<List<CartItem>> = dao.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Total
    val totalCart: StateFlow<Int> = cartItems.map { list ->
        list.sumOf { it.precioTotal }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Historial
    val orderHistory: StateFlow<List<OrderItem>> = dao.getOrderHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cupon descuento
    var discountCode by mutableStateOf("")
    var isDiscountApplied by mutableStateOf(false)

    fun applyDiscount() {
        if (discountCode.trim().uppercase() == "FELICES50") {
            isDiscountApplied = true
        } else {
            isDiscountApplied = false
        }
    }
    fun addToCart(item: CartItem) = viewModelScope.launch { dao.insert(item) }
    fun removeFromCart(item: CartItem) = viewModelScope.launch { dao.delete(item) }
    fun updateQuantity(item: CartItem, qty: Int) {
        if (qty > 0) viewModelScope.launch { dao.update(item.copy(cantidad = qty)) }
    }
    fun clearCart() { viewModelScope.launch { dao.clearCart() }
    }


    // Confirma compra y genera la boleta
    fun confirmarCompra(onBoletaGenerated: (String) -> Unit) {
        val currentItems = cartItems.value
        if (currentItems.isEmpty()) return

        val subtotal = currentItems.sumOf { it.precioTotal }

        // calculo descuento
        val discountAmount = if (isDiscountApplied) (subtotal * 0.50).toInt() else 0
        val totalFinal = subtotal - discountAmount

        // resumen para la base de datos
        val resumenDb = currentItems.joinToString("\n") { "${it.cantidad}x ${it.nombre}" }

        // texto de la boleta (Visual)
        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val detalleBoleta = currentItems.joinToString("\n") {
            "${it.cantidad} x ${it.nombre.take(20).padEnd(20)} $${it.precioTotal}"
        }
        val descuentoTexto = if (isDiscountApplied) {
            "DESCUENTO (50%):        -$${discountAmount}"
        } else {
            "DESCUENTO:              $0"
        }

        val boletaTexto = """
            --------------- BOLETA ---------------
            MIL SABORES PASTELERÍA
            Fecha: $fecha
            --------------------------------------
            CANT  DESCRIPCIÓN          TOTAL
            --------------------------------------
            $detalleBoleta
            --------------------------------------
            SUBTOTAL:                $$subtotal
            $descuentoTexto
            --------------------------------------
            TOTAL A PAGAR:           $$totalFinal
            --------------------------------------
            ¡Gracias por su preferencia!
        """.trimIndent()

        viewModelScope.launch {
            dao.insertOrder(OrderItem(itemsResumen = resumenDb, totalPagado = totalFinal))
            dao.clearCart()
            onBoletaGenerated(boletaTexto)
        }
    }
}