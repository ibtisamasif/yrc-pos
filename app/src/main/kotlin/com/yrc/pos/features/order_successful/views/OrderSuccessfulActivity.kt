package com.yrc.pos.features.order_successful.views

import android.os.Bundle
import android.os.PersistableBundle
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity

class OrderSuccessfulActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_order_successful)
    }
}