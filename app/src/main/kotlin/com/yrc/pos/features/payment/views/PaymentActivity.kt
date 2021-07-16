package com.yrc.pos.features.payment.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderRequest
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderResponse
import com.yrc.pos.features.order_successful.views.OrderSuccessfulActivity
import com.yrc.pos.features.payment.payment_service.RegisterOrderRequest
import com.yrc.pos.features.payment.payment_service.RegisterOrderResponse
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : YrcBaseActivity() {

    var vouchersApplied = PaymentVM.giftVouchers.oldVouchersRedeemedTotal.toDouble() + PaymentVM.giftVouchers.newVouchersRedeemedTotal.toDouble()

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
        textViewSubtotalAmount.text = "£${PaymentVM.orderSubTotal}"
        textViewVouchersAppliedAmount.text = "£${vouchersApplied}"
        textViewTotalAmount.text = "£${(PaymentVM.orderSubTotal - vouchersApplied)}"
    }

    private fun setListeners() {

        buttonReturnToBasket.setOnClickListener {
            finish()
        }

        buttonPayNow.setOnClickListener {
            APiManager.postRegisterOrder(this, this, getRegisterOrderRequest())
        }
    }

    private fun getRegisterOrderRequest(): RegisterOrderRequest {
        return RegisterOrderRequest(
            Build.SERIAL,
            TicketVM.enclosure.name,
            PaymentVM.paymentMethod,
            PaymentVM.orderSubTotal.toString(),
            PaymentVM.giftVouchers,
            PaymentVM.tickets,
            PaymentVM.orderTotal.toString()
        )
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        when (apiResponse) {
            is RegisterOrderResponse -> {
                apiResponse.orderId?.let { APiManager.postCompleteOrder(this, this, CompleteOrderRequest(Build.SERIAL, it, "PAID")) }
            }
            is CompleteOrderResponse -> {
                startActivity(Intent(this, OrderSuccessfulActivity::class.java))
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        super.onApiFailure(errorCode)
        Toast.makeText(this, "error code: $errorCode", Toast.LENGTH_SHORT).show()
    }
}