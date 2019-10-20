package com.yrc.pos.features.forget_password.new_password_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants

class NewPasswordRequest {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("number")
    var number: String? = null

    @SerializedName("otp_code")
    var otpCode: String? = Constants.EMPTY_STRING

    @SerializedName("new_password")
    var newPassword: String? = Constants.EMPTY_STRING
}