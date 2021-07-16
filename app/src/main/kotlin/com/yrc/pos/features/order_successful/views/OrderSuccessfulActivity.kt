package com.yrc.pos.features.order_successful.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.TicketPrintUtils
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderResponse
import kotlinx.android.synthetic.main.activity_order_successful.*

class OrderSuccessfulActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successful)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {

        val completeOrderResponse = intent.extras?.getSerializable(ORDER_ID) as CompleteOrderResponse?
        completeOrderResponse?.let {
            textView.text = "Order Successful : #${it.orderId}"
            it.qrs?.let { qrs -> fillQrCodesInSelectedTickets(qrs) }
            TicketPrintUtils.printTicket(this, TicketVM.selectedTickets)
        }
        updatePaymentDetailSection()

    }

    private fun fillQrCodesInSelectedTickets(qrs: List<String>) { // TODO replace with actual QR code
        TicketVM.selectedTickets.forEach { oneTicket ->
            oneTicket.quantity?.let {
                for (i in 1..it) {
                    oneTicket.qrCode = "0157820702387"
                }
            }
        }
    }

    private fun updatePaymentDetailSection() {
        textViewPaymentMethodValue.text = PaymentVM.paymentMethod.name.toUpperCase()
        textViewSubtotalAmount.text = "£${PaymentVM.orderSubTotal}"
        textViewVouchersAppliedAmount.text = "£${PaymentVM.giftVouchers.oldVouchersRedeemedTotal + PaymentVM.giftVouchers.newVouchersRedeemedTotal}"
        textViewTotalAmount.text = "£${(PaymentVM.orderSubTotal - (PaymentVM.giftVouchers.oldVouchersRedeemedTotal + PaymentVM.giftVouchers.newVouchersRedeemedTotal))}"

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