package com.yrc.pos.features.voucher.voucher_service

import com.google.gson.annotations.SerializedName

data class ValidateNewVoucherRequest(
    @SerializedName("device_id")
    val deviceID: String,

    val code: String,
    val pin: String,

    @SerializedName("order_total")
    val orderTotal: String
)
