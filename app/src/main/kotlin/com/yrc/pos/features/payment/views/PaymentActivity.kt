package com.yrc.pos.features.payment.views

import android.content.Intent
import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.features.order_successful.views.OrderSuccessfulActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setListeners()
    }

    private fun setListeners() {

        buttonReturnToBasket.setOnClickListener {
            finish()
        }

        buttonPayNow.setOnClickListener {
//            TicketPrintUtils.printTicket(this, )
            startActivity(Intent(this, OrderSuccessfulActivity::class.java))
        }
    }
}