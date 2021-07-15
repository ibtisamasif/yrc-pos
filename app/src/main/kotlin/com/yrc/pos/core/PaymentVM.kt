package com.yrc.pos.core

import com.yrc.pos.core.providers.models.Ticket
import com.yrc.pos.features.payment.payment_service.GiftVouchers
import com.yrc.pos.features.payment.payment_service.NewVouchersRedeemed
import kotlin.properties.Delegates

object PaymentVM {
    lateinit var paymentMethod: PaymentMethod
    var orderSubTotal by Delegates.notNull<Double>()
    lateinit var giftVouchers: GiftVouchers
    lateinit var tickets: ArrayList<Ticket>
    var orderTotal by Delegates.notNull<Double>()

    fun clear() {
        orderSubTotal = 0.0
        giftVouchers = GiftVouchers("", "", arrayListOf(NewVouchersRedeemed("", "")))
        tickets = arrayListOf()
        orderTotal = 0.0
    }
}

enum class PaymentMethod {
    cash, card, none
}
