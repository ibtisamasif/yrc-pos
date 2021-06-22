package com.yrc.pos.features.login.login_service

import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.services.YrcBaseApiResponse

data class LoginResponse(
    var enclosure: Enclosure? = null
) : YrcBaseApiResponse()