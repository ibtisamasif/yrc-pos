package com.yrc.pos.core

import android.app.Application
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User

class YrcApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        APiManager.initialize(applicationContext)
        User.initialize(applicationContext)
        Session.initialize(applicationContext)
    }
}