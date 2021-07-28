package com.yrc.pos.features.order_successful.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yrc.pos.R
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.TicketPrintUtils
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.features.dashboard.DashboardActivity
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderResponse
import com.yrc.pos.features.voucher.viewmodels.NewVouchersVM
import com.yrc.pos.features.voucher.viewmodels.OldVoucherVM
import kotlinx.android.synthetic.main.activity_order_successful.*

class OrderSuccessfulActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successful)
        setListeners()
        updateUI()
    }

    private fun updateUI() {

        printTicketAndShowOrderId()
        updatePaymentDetailSection()

    }

    private fun printTicketAndShowOrderId() {
        val completeOrderResponse =
            intent.extras?.getSerializable(COMPLETE_ORDER_RESPONSE) as CompleteOrderResponse?
        completeOrderResponse?.let {
            it.qrs?.let { qrs -> fillQrCodesInSelectedTickets(qrs) }
            it.orderId?.let { it1 ->
                TicketPrintUtils.printTicket(
                    this,
                    TicketVM.selectedTickets,
                    it1
                )
            }
            textView.text = resources.getString(R.string.order_successful, it.orderId)
        } ?: kotlin.run {
            textView.setBackgroundColor(resources.getColor(R.color.colorRedError))
            textView.setTextColor(resources.getColor(R.color.colorWhite))
            textView.text = resources.getString(R.string.order_unsuccessful)
            buttonReprintQRs.visibility = View.GONE
        }
    }

    private fun fillQrCodesInSelectedTickets(qrs: List<String>) { // TODO replace with actual QR code
        try {
            var count = 0
            TicketVM.selectedTickets.forEach { oneTicket ->
                oneTicket.quantity.let {
                    for (i in 1..it) {
                        oneTicket.qrCode = qrs[count]
                        count++
                    }
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun updatePaymentDetailSection() {
        textViewPaymentMethodValue.text = PaymentVM.paymentMethod.name.toUpperCase()
        textViewSubtotalAmount.text = "£${PaymentVM.orderSubTotal}"
        textViewVouchersAppliedAmount.text =
            "£${PaymentVM.giftVouchers.oldVouchersRedeemedTotal + PaymentVM.giftVouchers.newVouchersRedeemedTotal}"
        textViewTotalAmount.text =
            "£${(PaymentVM.orderSubTotal - (PaymentVM.giftVouchers.oldVouchersRedeemedTotal + PaymentVM.giftVouchers.newVouchersRedeemedTotal))}"

    }

    private fun setListeners() {

        buttonReprintQRs.setOnClickListener {
            printTicketAndShowOrderId()
        }

        buttonNewOrder.setOnClickListener {
            TicketVM.clear()
            OldVoucherVM.clear()
            NewVouchersVM.clear()
            PaymentVM.clear()
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

    }

    companion object {
        const val COMPLETE_ORDER_RESPONSE = "complete_order_response"
    }
}