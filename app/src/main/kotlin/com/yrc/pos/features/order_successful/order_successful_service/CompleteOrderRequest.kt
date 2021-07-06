package com.yrc.pos.features.order_successful.order_successful_service

import com.google.gson.annotations.SerializedName

data class CompleteOrderRequest (
    @SerializedName("device_id")
    val deviceID: String,

    @SerializedName("order_id")
    val orderID: String,

    val status: String
)
