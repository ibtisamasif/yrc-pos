package com.yrc.pos.core

object PaymentVM {
    lateinit var paymentMethod: PaymentMethod
}

enum class PaymentMethod {
    cash, card
}
