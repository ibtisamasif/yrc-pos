package com.yrc.pos.core.services

import com.yrc.pos.core.EndPoints
import com.yrc.pos.core.providers.TicketModel
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST(EndPoints.API_LOGIN)
    fun loginToYrc(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET(EndPoints.API_GRANDSTAND)
    fun grandStand(): Call<TicketModel>

    @GET(EndPoints.API_CLOCKTOWER)
    fun clockTower(): Call<TicketModel>
}