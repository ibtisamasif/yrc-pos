package com.yrc.pos.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.TicketVM.deviceSerial
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.providers.models.TicketModel
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.dashboard.DashboardActivity
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse


class SplashActivity : YrcBaseActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000 //2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        mDelayHandler?.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            APiManager.loginApi(this, this, LoginRequest(deviceSerial))
        }
    }

    @SuppressLint("HardwareIds")
    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)
        when (apiResponse) {
            is LoginResponse -> {
                apiResponse.enclosure?.let {
                    APiManager.getTicketInfo(this, this, it, deviceSerial)
                }
            }
            is TicketModel -> {
                apiResponse.tickets?.let {
                    TicketVM.originalTickets = apiResponse.tickets
                    apiResponse.enclosure?.let {
                        TicketVM.enclosure = it
                        moveToDashboardActivity(apiResponse)
                    }
                }
            }
        }
    }

    private fun moveToDashboardActivity(apiResponse: TicketModel) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        dashboardIntent.putExtra("race_day_title", apiResponse.meetDayName)
        dashboardIntent.putExtra("race_day_date", apiResponse.meetDayDate)
        startActivity(dashboardIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelayHandler?.removeCallbacks(mRunnable)
    }
}
