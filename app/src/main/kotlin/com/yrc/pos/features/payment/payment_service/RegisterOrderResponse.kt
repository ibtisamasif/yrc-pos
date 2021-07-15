package com.yrc.pos.features.payment.payment_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.services.YrcBaseApiResponse

class RegisterOrderResponse(
    @SerializedName("order_id")
    val orderId: Int
) : YrcBaseApiResponse()