package com.yrc.pos.core.providers.models

class YrcError(
    var errorCode: Int = 0,
    message: String?
) : Throwable(message)