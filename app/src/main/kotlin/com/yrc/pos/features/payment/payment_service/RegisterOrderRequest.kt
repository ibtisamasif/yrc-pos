package com.yrc.pos.features.payment.payment_service

import com.google.gson.annotations.SerializedName


data class RegisterOrderRequest (
    @SerializedName("device_id")
    val deviceID: String,

    val enclosure: String,

    @SerializedName("payment_method")
    val paymentMethod: String,

    @SerializedName("order_sub_total")
    val orderSubTotal: String,

    @SerializedName("gift_vouchers")
    val giftVouchers: GiftVouchers,

    val tickets: List<Ticket>,

    @SerializedName("order_total")
    val orderTotal: String
)

data class GiftVouchers (
    @SerializedName("old_vouchers_redeemed_total")
    val oldVouchersRedeemedTotal: String,

    @SerializedName("new_vouchers_redeemed_total")
    val newVouchersRedeemedTotal: String,

    @SerializedName("new_vouchers_redeemed")
    val newVouchersRedeemed: List<NewVouchersRedeemed>
)

data class NewVouchersRedeemed (
    val code: String,
    val value: String
)

data class Ticket (
    val name: String,
    val price: String,

    @SerializedName("ticket_price_id")
    val ticketPriceID: Long,

    val quantity: Long
)
