package com.yrc.pos.core.services

import android.content.Context
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APiManager {

    private lateinit var hambaServices: ApiInterface
    private const val BASE_URL = "https://kiosk.yorkracecourse.co.uk"

    fun initialize(){
        hambaServices = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun loginApi(context: Context, apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        val loginApiCall = hambaServices.loginToYrc(loginRequest)
        ApiExecutor<LoginResponse>().addCallToQueue(context, loginApiCall, apiCallbacks)
    }
}