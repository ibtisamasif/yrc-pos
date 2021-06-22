package com.yrc.pos.core.providers

import android.annotation.SuppressLint
import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

object ProgressDialogProvider {

    @SuppressLint("StaticFieldLeak")
    private lateinit var progressHud: KProgressHUD
    private var isAlreadyShowing: Boolean = false

    fun show(context: Context) {
        if (!isAlreadyShowing)
            progressHud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        isAlreadyShowing = true
    }

    fun dismiss() {
        isAlreadyShowing = false
        progressHud.dismiss()
    }
}