package com.yrc.pos.core.services

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.providers.models.TicketModel
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderRequest
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderResponse
import com.yrc.pos.features.payment.payment_service.RegisterOrderRequest
import com.yrc.pos.features.payment.payment_service.RegisterOrderResponse
import com.yrc.pos.features.voucher.voucher_service.ValidateNewVoucherRequest
import com.yrc.pos.features.voucher.voucher_service.ValidateNewVoucherResponse
import com.yrc.pos.features.voucher.voucher_service.ValidateOldVoucherRequest
import com.yrc.pos.features.voucher.voucher_service.ValidateOldVoucherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APiManager {

    private lateinit var yrcServices: ApiInterface
    private const val BASE_URL = "https://kiosk.yorkracecourse.co.uk"

    private fun getHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging)
            .addInterceptor(ChuckerInterceptor(context))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.MINUTES).build()
    }

    fun initialize(applicationContext: Context) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        yrcServices = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient(applicationContext))
            .build()
            .create(ApiInterface::class.java)
    }

    fun loginApi(context: Context, apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        ApiExecutor<LoginResponse>().addCallToQueue(context, yrcServices.loginToYrc(loginRequest), apiCallbacks)
    }

    fun getTicketInfo(context: Context, apiCallbacks: ApiCallbacks, enclosure: Enclosure, id: String) {
        ApiExecutor<TicketModel>().addCallToQueue(context, yrcServices.getTicketInfo(enclosure, id), apiCallbacks)
    }

    fun postValidateOldVoucher(context: Context, apiCallbacks: ApiCallbacks, validateOldVoucherRequest: ValidateOldVoucherRequest) {
        ApiExecutor<ValidateOldVoucherResponse>().addCallToQueue(context, yrcServices.postValidateOldVoucherRequest(validateOldVoucherRequest), apiCallbacks)
    }

    fun postValidateNewVoucher(context: Context, apiCallbacks: ApiCallbacks, validateNewVoucherRequest: ValidateNewVoucherRequest) {
        ApiExecutor<ValidateNewVoucherResponse>().addCallToQueue(context, yrcServices.postValidateNewVoucherRequest(validateNewVoucherRequest), apiCallbacks)
    }

    fun postRegisterOrder(context: Context, apiCallbacks: ApiCallbacks, registerOrderRequest: RegisterOrderRequest) {
        ApiExecutor<RegisterOrderResponse>().addCallToQueue(context, yrcServices.postRegisterOrder(registerOrderRequest), apiCallbacks)
    }

    @JvmStatic
    fun postCompleteOrder(context: Context, apiCallbacks: ApiCallbacks, completeOrderRequest: CompleteOrderRequest) {
        ApiExecutor<CompleteOrderResponse>().addCallToQueue(context, yrcServices.postCompleteOrder(completeOrderRequest), apiCallbacks)
    }
}