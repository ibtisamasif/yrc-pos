package com.yrc.pos.features.profile.edit_profile_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import java.util.ArrayList

class IndividualProfileEditRequest {

        @SerializedName("personType")
        var personType: String? = Constants.EMPTY_STRING

        @SerializedName("nationality")
        var nationality: String? = Constants.EMPTY_STRING

        @SerializedName("birthDate")
        var birthDate: String? = Constants.EMPTY_STRING

        @SerializedName("prefix")
        var prefix: String? = Constants.EMPTY_STRING

        @SerializedName("gender")
        var gender: String? = Constants.EMPTY_STRING

        @SerializedName("registrationNo")
        var registrationNo: String? = Constants.EMPTY_STRING

        @SerializedName("registrationType")
        var registrationType: String? = Constants.EMPTY_STRING

        @SerializedName("imgExtension")
        var imgExtension: String? = Constants.EMPTY_STRING

        @SerializedName("interests")
        var interests: List<String> = ArrayList()

        @SerializedName("firstName")
        var firstName: String? = Constants.EMPTY_STRING

        @SerializedName("lastName")
        var lastName: String? = Constants.EMPTY_STRING

        @SerializedName("middleName")
        var middleName: String? = Constants.EMPTY_STRING

        @SerializedName("number")
        var number: String? = Constants.EMPTY_STRING

        @SerializedName("addressType")
        var addressType: String? = Constants.EMPTY_STRING

        @SerializedName("address")
        var address: String? = Constants.EMPTY_STRING

        @SerializedName("cityName")
        var cityName: String? = Constants.EMPTY_STRING

        @SerializedName("country")
        var country: String? = Constants.EMPTY_STRING

        @SerializedName("zipCode")
        var zipCode: String? = Constants.EMPTY_STRING

        @SerializedName("companyName")
        var companyName: String? = Constants.EMPTY_STRING

        @SerializedName("enableNotification")
        var enableNotification: Boolean? = false

        @SerializedName("enableNumberVisibility")
        var enableNumberVisibility: Boolean? = false

        @SerializedName("enableAgeVisibility")
        var enableAgeVisibility: Boolean? = false

        @SerializedName("email")
        var email: String? = Constants.EMPTY_STRING

        @SerializedName("avatar")
        var avatar: String? = Constants.EMPTY_STRING

        @SerializedName("employee")
        var employee: Boolean? = false
}