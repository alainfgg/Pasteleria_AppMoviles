package com.example.login001v.view.common.faq

data class FaqItemData(
    val id: Int,
    val question: String,
    val answer: String
)

val faqDataList = listOf(
    FaqItemData(
        id = 1,
        question = "¿Cuáles son los métodos de pago?",
        answer = "Aceptamos todo medio de pago"
    ),
    FaqItemData(
        id = 2,
        question = "¿A qué zonas de Santiago despachan?",
        answer = "Realizamos entregas a domicilio en toda la zona de Puente Alto y La Florida"
    ),
    FaqItemData(
        id = 3,
        question = "¿Cómo realizo un pedido?",
        answer = "Es muy fácil. Revisa nuestro catálogo de productos, elige tu pastel favorito y procede a realizar el pago a través de nuestra plataforma segura. Si tienes dudas, contáctanos vía Instagram @pasteleria-mil-sabores o por WhatsApp al +56 9 12345678."
    ),
    FaqItemData(
        id = 4,
        question = "¿Ofrecen opciones para personas con alergias o dietas especiales?",
        answer = "Sí, ofrecemos opciones para personas con alergias o dietas especiales. Por favor, contáctanos para más información."
    )
)