package com.yrc.pos.features.order_successful.order_successful_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.services.YrcBaseApiResponse

class CompleteOrderResponse(
    @SerializedName("order_id")
    val orderId: Int?,
    val qrs: List<String>?
) : YrcBaseApiResponse()