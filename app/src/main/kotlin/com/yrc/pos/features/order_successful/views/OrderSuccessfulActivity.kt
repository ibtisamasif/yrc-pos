package com.yrc.pos.features.order_successful.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.TicketPrintUtils
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import kotlinx.android.synthetic.main.activity_order_successful.*

class OrderSuccessfulActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successful)

        initViews()
        setListeners()

        TicketPrintUtils.printTicket(this, TicketVM.selectedTickets)
    }

    private fun initViews() {
        intent.extras?.getString(ORDER_ID)?.let {
            textView.text = "Order Successful : #$it"
        }
    }

    private fun setListeners() {

        buttonReprintQRs.setOnClickListener {
            TicketPrintUtils.printTicket(this, TicketVM.selectedTickets)
        }

        buttonNewOrder.setOnClickListener {
            TicketVM.clear()
            PaymentVM.clear()
            finish()
        }

    }

    companion object {
        const val ORDER_ID = "order_id"
    }
}