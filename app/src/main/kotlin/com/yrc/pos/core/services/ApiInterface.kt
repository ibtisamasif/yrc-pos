package com.yrc.pos.core.services

import com.yrc.pos.core.EndPoints
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.providers.TicketModel
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST(EndPoints.API_LOGIN)
    fun loginToYrc(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET(EndPoints.API_GET_TICKET_INFO)
    fun getTicketInfo(
        @Path ("enclosure") enclosure: Enclosure,
        @Query("device_id") id: String
    ): Call<TicketModel>
}