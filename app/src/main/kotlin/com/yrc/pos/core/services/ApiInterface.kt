package com.yrc.pos.core.services

import com.yrc.pos.core.EndPoints
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

    @POST(EndPoints.API_POST_VALIDATE_OLD_VOUCHER)
    fun postValidateOldVoucherRequest(
        @Body validateOldVoucherRequest: ValidateOldVoucherRequest
    ): Call<ValidateOldVoucherResponse>

    @POST(EndPoints.API_POST_VALIDATE_NEW_VOUCHER)
    fun postValidateNewVoucherRequest(
        @Body validateNewVoucherRequest: ValidateNewVoucherRequest
    ): Call<ValidateNewVoucherResponse>

    @POST(EndPoints.API_POST_REGISTER_ORDER)
    fun postRegisterOrder(
        @Body registerOrderRequest: RegisterOrderRequest
    ): Call<RegisterOrderResponse>

    @POST(EndPoints.API_POST_COMPLETE_ORDER)
    fun postCompleteOrder(
        @Body completeOrderRequest: CompleteOrderRequest
    ): Call<CompleteOrderResponse>
}