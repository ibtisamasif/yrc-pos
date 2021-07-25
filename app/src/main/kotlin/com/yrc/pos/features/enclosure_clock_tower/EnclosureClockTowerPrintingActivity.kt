package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.PaymentMethod
import com.yrc.pos.core.PaymentVM
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.providers.models.Ticket
import com.yrc.pos.features.payment.payment_service.GiftVouchers
import com.yrc.pos.features.payment.views.PaymentActivity
import com.yrc.pos.features.voucher.viewmodels.NewVouchersVM
import com.yrc.pos.features.voucher.viewmodels.OldVoucherVM
import com.yrc.pos.features.voucher.views.NewVoucherActivity
import com.yrc.pos.features.voucher.views.OldVoucherActivity
import kotlinx.android.synthetic.main.activity_enclosure_clock_tower_printing.*


class EnclosureClockTowerPrintingActivity : YrcBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enclosure_clock_tower_printing)

        setListeners()
    }

    override fun onResume() {
        super.onResume()
        removeTemporarySelection()
        updateUI()
    }

    private fun removeTemporarySelection() {
        TicketVM.selectedTickets.forEach {
            it.isTemporarySelected = false
        }
    }

    private fun setListeners() {
        button_adult?.setOnClickListener {
            if (button_adult.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
            TicketVM.originalTickets[0].ticketPriceID?.let { it1 -> setTemporarySelection(it1) }
        }
        button_over65?.setOnClickListener {
            if (button_over65.isPressed) {
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
            TicketVM.originalTickets[1].ticketPriceID?.let { it1 -> setTemporarySelection(it1) }
        }

        button_back.setOnClickListener {
            onBackPressed()
        }

        button_minus.setOnClickListener {
            getTemporarySelectedTicket()?.let {
                if (it.quantity > 0) {
                    it.quantity -= 1
                } else Toast.makeText(this, "Reached max limit", Toast.LENGTH_SHORT).show()
            } ?: kotlin.run {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            updateUI()
        }

        button_plus.setOnClickListener {
            getTemporarySelectedTicket()?.let {
                if (it.quantity < 10) {
                    it.quantity += 1
                } else Toast.makeText(this, "Reached max limit", Toast.LENGTH_SHORT).show()
            } ?: kotlin.run {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            updateUI()
        }

        button_clear.setOnClickListener {
            TicketVM.selectedTickets.clear()
            Toast.makeText(this, "Cleared all selections and reset", Toast.LENGTH_SHORT).show()
            finish()
        }

        button_gv_old.setOnClickListener {
            startActivity(Intent(this, OldVoucherActivity::class.java))
        }

        button_gv_new.setOnClickListener {
            startActivity(Intent(this, NewVoucherActivity::class.java))
        }

        button_card.setOnClickListener {
            PaymentVM.paymentMethod = PaymentMethod.card
            PaymentVM.orderSubTotal = TicketVM.getSubtotal()
            PaymentVM.giftVouchers = GiftVouchers(OldVoucherVM.oldVoucherRedeemedTotal, NewVouchersVM.newVouchersRedeemedTotal, NewVouchersVM.newVouchersRedeemed)
            PaymentVM.tickets = TicketVM.selectedTickets
            PaymentVM.orderTotal = (TicketVM.getSubtotal() - (OldVoucherVM.oldVoucherRedeemedTotal + NewVouchersVM.newVouchersRedeemedTotal))
            startActivity(Intent(this, PaymentActivity::class.java))
        }

        button_cash.setOnClickListener {
            PaymentVM.paymentMethod = PaymentMethod.cash
            PaymentVM.orderSubTotal = TicketVM.getSubtotal()
            PaymentVM.giftVouchers = GiftVouchers(OldVoucherVM.oldVoucherRedeemedTotal, NewVouchersVM.newVouchersRedeemedTotal, NewVouchersVM.newVouchersRedeemed)
            PaymentVM.tickets = TicketVM.selectedTickets
            PaymentVM.orderTotal = (TicketVM.getSubtotal() - (OldVoucherVM.oldVoucherRedeemedTotal + NewVouchersVM.newVouchersRedeemedTotal))
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }

    private fun getTemporarySelectedTicket(): Ticket? {
        TicketVM.selectedTickets.forEach {
            if (it.isTemporarySelected) return it
        }
        return null
    }

    private fun setTemporarySelection(ticketId: Int) {
        TicketVM.selectedTickets.forEach {
            it.isTemporarySelected = it.ticketPriceID == ticketId
        }
    }

    private fun updateUI() {
        TicketVM.originalTickets[0].ticketPriceID?.let {
            TicketVM.getTicketByTicketPriceIdFromSelectedTicketList(it)?.let { ticket ->
                button_adult.visibility = View.VISIBLE
                button_adult.text = ticket.quantity.toString().plus(" ").plus("x ${ticket.name} £".plus(" ").plus(ticket.quantity.times(ticket.price?.toDouble()!!)))
            }
        } ?: kotlin.run {
            button_adult.visibility = View.GONE
        }

        TicketVM.originalTickets[1].ticketPriceID?.let {
            TicketVM.getTicketByTicketPriceIdFromSelectedTicketList(it)?.let { ticket ->
                button_over65.visibility = View.VISIBLE
                button_over65.text = ticket.quantity.toString().plus(" ").plus("x ${ticket.name} £".plus(" ").plus(ticket.quantity.times(ticket.price?.toDouble()!!)))
            }
        } ?: kotlin.run {
            button_over65.visibility = View.GONE
            button_over65.invalidate()
        }

        button_total.text = TicketVM.getTotalText()
    }

}