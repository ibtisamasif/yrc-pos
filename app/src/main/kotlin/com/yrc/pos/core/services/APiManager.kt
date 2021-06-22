package com.yrc.pos.core.services

import android.content.Context
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.providers.TicketModel
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APiManager {

    private lateinit var yrcServices: ApiInterface
    private const val BASE_URL = "https://kiosk.yorkracecourse.co.uk"

    fun initialize(){
        yrcServices = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun loginApi(context: Context, apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        ApiExecutor<LoginResponse>().addCallToQueue(context, yrcServices.loginToYrc(loginRequest), apiCallbacks)
    }

    fun getTicketInfo(context: Context, apiCallbacks: ApiCallbacks, enclosure: Enclosure, id: String) {
        ApiExecutor<TicketModel>().addCallToQueue(context, yrcServices.getTicketInfo(enclosure, id), apiCallbacks)
    }
}