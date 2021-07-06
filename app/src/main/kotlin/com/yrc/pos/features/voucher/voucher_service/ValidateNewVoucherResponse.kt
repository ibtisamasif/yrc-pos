package com.yrc.pos.features.voucher.voucher_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.services.YrcBaseApiResponse

data class ValidateNewVoucherResponse(
    @SerializedName("voucher_amount")
    val voucherAmount: Int,

    @SerializedName("new_order_total")
    val newOrderTotal: Int
) : YrcBaseApiResponse()
