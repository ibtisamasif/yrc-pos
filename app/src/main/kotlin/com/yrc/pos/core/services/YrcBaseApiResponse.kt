package com.yrc.pos.core.services

abstract class YrcBaseApiResponse {

    var statusCode: Int? = null
    var statusMessage: String? = null

    var status: String? = null
    var message: String? = null
}