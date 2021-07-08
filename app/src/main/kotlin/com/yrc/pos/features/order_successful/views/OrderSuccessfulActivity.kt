package com.yrc.pos.features.order_successful.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import kotlinx.android.synthetic.main.activity_order_successful.*

class OrderSuccessfulActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successful)

        setListeners()
    }

    private fun setListeners() {
        buttonNewOrder.setOnClickListener {
            finish()
        }
    }
}