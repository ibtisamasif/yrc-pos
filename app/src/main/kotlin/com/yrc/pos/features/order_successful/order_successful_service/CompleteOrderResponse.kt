package com.yrc.pos.features.order_successful.order_successful_service

import com.yrc.pos.core.services.YrcBaseApiResponse

class CompleteOrderResponse(
    val qrs: List<String>?
) : YrcBaseApiResponse()