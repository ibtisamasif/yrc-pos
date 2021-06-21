package com.yrc.pos.features.login.login_service

import com.yrc.pos.core.services.YrcBaseApiResponse
import java.io.Serializable

data class LoginResponse(
    var enclosure: Enclosure? = null
) : YrcBaseApiResponse()


enum class Enclosure : Serializable {
    grandstand,
    clocktower;
}