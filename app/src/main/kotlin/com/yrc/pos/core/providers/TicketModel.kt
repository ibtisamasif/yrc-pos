package com.yrc.pos.core.providers

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.services.YrcBaseApiResponse

data class TicketModel (
    val tickets: List<Ticket>,
    @SerializedName("meet_day_name")
    val meetDayName: String,
    @SerializedName("meet_day_date")
    val meetDayDate: String,
    @SerializedName("meet_day_id")
    val meetDayID: Long,
    val enclosure: String

):java.io.Serializable,YrcBaseApiResponse()


data class Ticket (
    val name: String,
    val price: String
):java.io.Serializable
