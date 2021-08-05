package com.yrc.pos.core.services

import android.content.Context
import com.yrc.pos.R
import com.yrc.pos.core.YrcUtils
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.providers.models.YrcError
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiExecutor<T> : Callback<T> {

    private lateinit var mApiCallbacks: ApiCallbacks

    override fun onResponse(call: Call<T>, response: Response<T>) {

        mApiCallbacks.doAfterApiCall()

        if (response.isSuccessful){
            val apiResponse = response.body() as YrcBaseApiResponse
            apiResponse.statusCode = response.code()
            apiResponse.statusMessage = response.message()
            mApiCallbacks.onApiSuccess(apiResponse)
        } else {
            doOnError(response)
        }
    }

//    override fun onFailure(call: Call<T>, t: Throwable) {
//        mApiCallbacks.doAfterApiCall()
//        mApiCallbacks.onApiFailure(HttpErrorCodes.Unknown.code)
//    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (!call.isCanceled) {
            mApiCallbacks.doAfterApiCall()
            mApiCallbacks.onApiFailure(
                YrcError(HttpErrorCodes.Unknown.code, t.localizedMessage)
            )
        }
    }

    private fun doOnError(response: Response<T>) {
        try {
            response.errorBody()?.byteString()?.utf8()?.let {
                if (it.isNotEmpty()) {
                    val errorString = JSONObject(it).getString("error")
                    doOnErrorMsgFound(response, errorString)
                } else {
                    doOnErrorMsgNotFound(response)
                }
            }
        } catch (t: Throwable) {
            doOnErrorMsgNotFound(response)
        }
    }

    private fun doOnErrorMsgFound(response: Response<T>, error: String) {
        val errorString: String = error
        mApiCallbacks.onApiFailure(
            YrcError(
                response.code(), errorString
            )
        )
    }

    private fun doOnErrorMsgNotFound(response: Response<T>) {
        val errorString = ""
        mApiCallbacks.onApiFailure(
            YrcError(
                response.code(), errorString
            )
        )
    }

    fun addCallToQueue(context: Context, apiCall: Call<T>, apiCallbacks: ApiCallbacks) {
        if (YrcUtils.isNetworkAvailable(context)){
            this.mApiCallbacks = apiCallbacks
            apiCallbacks.doBeforeApiCall()
            apiCall.enqueue(this)
        } else {
            AlertDialogProvider.showAlertDialog(context, DialogTheme.ThemeWhite, context.getString(R.string.no_network_available))
        }
    }
}