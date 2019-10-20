package com.yrc.pos.features.forget_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.features.login.LoginActivity

class PasswordResetStatusActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_status)
    }

    fun onLoginButtonClicked(loginBtn: View) {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        finish()
    }
}