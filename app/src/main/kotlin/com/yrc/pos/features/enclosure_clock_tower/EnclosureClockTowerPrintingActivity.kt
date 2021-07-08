package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pax.dal.exceptions.PrinterDevException
import com.yrc.pos.R
import com.yrc.pos.core.TicketData
import com.yrc.pos.core.TicketPrintUtils
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import com.yrc.pos.features.voucher.views.VoucherActivity
import kotlinx.android.synthetic.main.activity_enclosure_clock_tower_printing.*


class EnclosureClockTowerPrintingActivity : YrcBaseActivity() {
    private var button1Count: Int = 0
    private var button2Count: Int = 0

    private var selectedButton: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enclosure_clock_tower_printing)
        val bundle = intent.extras
        if (bundle != null) {
            button1Count = bundle.getInt(EnclosureClockTowerFragment.TICKET_ADULTS)
            button2Count = bundle.getInt(EnclosureClockTowerFragment.TICKET_OVER65)
        }

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

        button_cash.setOnClickListener {
            try {
                for (n in 1..button1Count)
                    TicketPrintUtils.printTicket(this, TicketData.originalTickets)
            } catch (ex: PrinterDevException) {
                ex.printStackTrace()
            }
        }
        updateUi()
    }

    fun onPlusButtonClicked(view: View) {
        onBackPressed()
    }

    fun onMinusButtonClicked(view: View) {
        when (selectedButton) {
            0 -> {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                if (button1Count > 0)
                    button1Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_ADULTS,
                        button1Count
                    )
                )
                updateUi()
            }
            2 -> {
                if (button2Count > 0)
                    button2Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_OVER65,
                        button2Count
                    )
                )
                updateUi()
            }
        }
    }

    fun onMultiplyButtonClicked(view: View) {
        when (selectedButton) {
            0 -> {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                button1Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_ADULTS,
                        button1Count
                    )
                )
                updateUi()
            }
            2 -> {
                button2Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_OVER65,
                        button2Count
                    )
                )
                updateUi()
            }
        }
    }

    fun onCrossButtonClicked(view: View) {
        RxBus.publish(RxEvent.buttonFunction("onCrossButtonClicked"))
        finish()
    }

    fun onGVOldButtonClicked(view: View) {
    }

    fun onGVNewButtonClicked(view: View) {
        startActivity(Intent(this, VoucherActivity::class.java))
    }

    fun onCardButtonClicked(view: View) {
        finish()
    }

    fun onCashButtonClicked(view: View) {
        finish()
    }

    private fun updateUi() {
        val adult = TicketData.originalTickets[0].price?.toDouble()?.toInt()
        val over65 = TicketData.originalTickets[1].price?.toDouble()?.toInt()
        adult?.let { adultPrice ->
            over65?.let { over65Price ->
                if (button1Count != 0)
                    button_adult.visibility = View.VISIBLE
                button_adult.text = button1Count.toString().plus(" ")
                    .plus("x Adult £".plus(" ").plus(button1Count.times(adultPrice)))

                if (button2Count != 0)
                    button_over65.visibility = View.VISIBLE
                button_over65.text = button2Count.toString().plus(" ")
                    .plus("x Over65 £".plus(" ").plus(button2Count.times(over65Price)))

                textView_ticket.text = button1Count.plus(button2Count).toString().plus(" ")
                    .plus(
                        "x Ticket £".plus(" ")
                            .plus(
                                button1Count.times(adultPrice)
                                    .plus(button2Count.times(over65Price))
                            )
                    )
            }
        }

    }
}