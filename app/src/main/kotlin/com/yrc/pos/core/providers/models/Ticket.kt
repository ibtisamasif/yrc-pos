package com.yrc.pos.core.providers.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ticket(
    val name: String? = null,
    val price: String? = null,

    @SerializedName("ticket_price_id")
    val ticketPriceID: Int? = null,

    val quantity: Int? = 0
) : Serializable
