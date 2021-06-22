package com.yrc.pos.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.yrc.pos.R
import com.yrc.pos.core.Prices
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.providers.TicketModel
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
            APiManager.loginApi(this, this, LoginRequest("123456789"))
            APiManager.grandStand(this, this)
            APiManager.clockTower(this, this)
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)
        when (apiResponse) {
            is LoginResponse -> {
                apiResponse.enclosure?.let { moveToDashboardActivity(it) }
            }
            is TicketModel -> {
                val price = apiResponse.tickets?.get(0)?.price
                price?.let {
                    Prices.PRICE_ADULT = it.toInt()
                }
            }
        }
    }

    private fun moveToDashboardActivity(enclosure: Enclosure) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        dashboardIntent.putExtra(DashboardActivity.ENCLOSURE, enclosure)
        startActivity(dashboardIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelayHandler?.removeCallbacks(mRunnable)
    }
}
