package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.TicketData
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.features.payment.views.PaymentActivity
import com.yrc.pos.features.voucher.views.VoucherActivity
import kotlinx.android.synthetic.main.activity_enclosure_clock_tower_printing.*


class EnclosureClockTowerPrintingActivity : YrcBaseActivity() {

    private var selectedButton: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enclosure_clock_tower_printing)

        setListeners()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun setListeners() {
        button_adult?.setOnClickListener {
            selectedButton = 1
            if (button_adult.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }
        button_over65?.setOnClickListener {
            selectedButton = 2
            if (button_over65.isPressed) {
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }

        button_back.setOnClickListener {
            onBackPressed()
        }

        button_minus.setOnClickListener {
            when (selectedButton) {
                0 -> {
                    Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    if (TicketData.selectedTickets[0].quantity > 0)
                        TicketData.selectedTickets[0].quantity -= 1
                }
                2 -> {
                    if (TicketData.selectedTickets[1].quantity > 0)
                        TicketData.selectedTickets[1].quantity -= 1
                }
            }
            updateUI()
        }

        button_plus.setOnClickListener {
            when (selectedButton) {
                0 -> {
                    Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    TicketData.selectedTickets[0].quantity += 1
                }
                2 -> {
                    TicketData.selectedTickets[1].quantity += 1
                }
            }
            updateUI()
        }

        button_clear.setOnClickListener {
            TicketData.selectedTickets.clear()
            Toast.makeText(this, "Cleared all selections and reset", Toast.LENGTH_SHORT).show()
            finish()
        }

        button_gv_new.setOnClickListener {
            startActivity(Intent(this, VoucherActivity::class.java))
        }

        button_card.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }

        button_cash.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }

    private fun updateUI() {
        TicketData.getTicketByTicketPriceIdFromSelectedTicketList(33)?.let { ticket ->
            button_adult.visibility = View.VISIBLE
            button_adult.text = ticket.quantity.toString().plus(" ").plus("x ${ticket.name} £".plus(" ").plus(ticket.quantity.times(ticket.price?.toDouble()!!)))
        }

        TicketData.getTicketByTicketPriceIdFromSelectedTicketList(34)?.let { ticket ->
            button_over65.visibility = View.VISIBLE
            button_over65.text = ticket.quantity.toString().plus(" ").plus("x ${ticket.name} £".plus(" ").plus(ticket.quantity.times(ticket.price?.toDouble()!!)))
        }

        button_total.text = TicketData.getTotalText()
    }

}