package com.yrc.pos.features.payment.views

import android.content.Intent
import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.order_successful.views.OrderSuccessfulActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setUp()
        initViews()
        setListeners()
    }

    private fun setUp() {
    }

    private fun initViews() {
        textViewPaymentMethodValue.text = PaymentVM.paymentMethod.name.toUpperCase()
    }

    private fun setListeners() {

        buttonReturnToBasket.setOnClickListener {
            finish()
        }

        buttonPayNow.setOnClickListener {
//            APiManager.postRegisterOrder(this, this, getRegisterOrderRequest())
        }
    }

//    private fun getRegisterOrderRequest(): RegisterOrderRequest {
//        return RegisterOrderRequest(
//            Build.SERIAL,
//            TicketVM.enclosure.name,
//            PaymentVM.paymentMethod
//        )
//    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        startActivity(Intent(this, OrderSuccessfulActivity::class.java))
    }
}