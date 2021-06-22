package com.yrc.pos.core.providers

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.services.YrcBaseApiResponse

data class TicketModel(
    val tickets: List<Ticket>? = null,
    @SerializedName("meet_day_name")
    val meetDayName: String? = null,
    @SerializedName("meet_day_date")
    val meetDayDate: String? = null,
    @SerializedName("meet_day_id")
    val meetDayID: Int? = null,
    val enclosure: Enclosure? = null
) : java.io.Serializable, YrcBaseApiResponse()


data class Ticket(
    val name: String? = null,
    val price: String? = null
) : java.io.Serializable
