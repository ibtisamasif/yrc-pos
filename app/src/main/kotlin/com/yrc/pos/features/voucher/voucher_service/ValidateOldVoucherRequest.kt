package com.yrc.pos.features.voucher.voucher_service

import com.google.gson.annotations.SerializedName

data class ValidateOldVoucherRequest (
    @SerializedName("device_id")
    val deviceID: String,

    val amount: String,

    @SerializedName("order_total")
    val orderTotal: String
)
