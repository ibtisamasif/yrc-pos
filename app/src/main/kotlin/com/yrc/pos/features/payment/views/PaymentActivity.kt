package com.yrc.pos.features.payment.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yrc.pos.R
import com.yrc.pos.core.PaymentMethod
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.TicketVM.deviceSerial
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderRequest
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderResponse
import com.yrc.pos.features.order_successful.views.OrderSuccessfulActivity
import com.yrc.pos.features.payment.payment_service.RegisterOrderRequest
import com.yrc.pos.features.payment.payment_service.RegisterOrderResponse
import eft.com.eftservicelib.EFTServiceLib
import kotlinx.android.synthetic.main.activity_payment.*


class PaymentActivity : YrcBaseActivity() {

    private var orderId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        updatePaymentDetailSection()
    }

    private fun updatePaymentDetailSection() {
        textViewPaymentMethodValue.text = PaymentVM.paymentMethod.name.toUpperCase()
        textViewSubtotalAmount.text = "£${PaymentVM.orderSubTotal}"
        textViewVouchersAppliedAmount.text =
            "£${PaymentVM.giftVouchers.oldVouchersRedeemedTotal + PaymentVM.giftVouchers.newVouchersRedeemedTotal}"
        textViewTotalAmount.text =
            "£${(PaymentVM.orderSubTotal - (PaymentVM.giftVouchers.oldVouchersRedeemedTotal + PaymentVM.giftVouchers.newVouchersRedeemedTotal.toDouble()))}"
    }

    private fun setListeners() {

        buttonReturnToBasket.setOnClickListener {
            finish()
        }

        buttonPayNow.setOnClickListener {
            if (TicketVM.selectedTickets.size > 0)
                APiManager.postRegisterOrder(this, this, getRegisterOrderRequest())
            else
                Toast.makeText(this, "Please select ticket first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRegisterOrderRequest(): RegisterOrderRequest {
        return RegisterOrderRequest(
            deviceSerial,
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
                apiResponse.orderId?.let {
                    when (PaymentVM.paymentMethod) {
                        PaymentMethod.cash -> {
                            APiManager.postCompleteOrder(
                                this,
                                this,
                                CompleteOrderRequest(deviceSerial, it, "PAID")
                            )
                        }
                        PaymentMethod.card -> {
                            orderId = it
                            EFTServiceLib.runTrans(this, PaymentVM.orderTotal.toInt() * 100, EFTServiceLib.TRANSACTION_TYPE_SALE, "", "", "", orderId.toString(), false)
                        }
                        else -> {
                            Toast.makeText(this, "Payment method missing", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            is CompleteOrderResponse -> {
                when (PaymentVM.paymentMethod) {
                    PaymentMethod.cash -> {
                        val intent = Intent(this, OrderSuccessfulActivity::class.java)
                        intent.putExtra(OrderSuccessfulActivity.ORDER_ID, apiResponse)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        super.onApiFailure(errorCode)
        Toast.makeText(this, "error code: $errorCode", Toast.LENGTH_SHORT).show()
    }
}