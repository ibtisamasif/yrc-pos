package com.yrc.pos.core

import androidx.fragment.app.Fragment
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.providers.ProgressDialogProvider
import com.yrc.pos.core.providers.models.YrcError
import com.yrc.pos.core.services.ApiCallbacks
import com.yrc.pos.core.services.YrcBaseApiResponse

abstract class YrcBaseFragment : Fragment(), ApiCallbacks {

    override fun doBeforeApiCall() {
        ProgressDialogProvider.show(activity!!)
    }

    override fun doAfterApiCall() {
        ProgressDialogProvider.dismiss()
    }

    override fun onApiFailure(error: YrcError) {
        AlertDialogProvider.showFailureDialog(activity!!, error, DialogTheme.ThemeWhite)
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {}
}