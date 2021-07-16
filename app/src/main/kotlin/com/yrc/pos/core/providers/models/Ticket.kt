package com.yrc.pos.core.providers.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ticket(
    var name: String? = null,
    var price: String? = null,
    @SerializedName("ticket_price_id")
    var ticketPriceID: Int? = null,
    var quantity: Int = 1,
    var qrCode: String? = null
) : Serializable
