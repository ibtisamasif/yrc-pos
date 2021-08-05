package com.yrc.pos.core.services

import com.yrc.pos.core.providers.models.YrcError

interface ApiCallbacks {

    fun doBeforeApiCall()

    fun doAfterApiCall()

    fun onApiFailure(error: YrcError)

    fun onApiSuccess(apiResponse: YrcBaseApiResponse)
}