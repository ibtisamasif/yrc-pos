package com.yrc.pos.features.enclosure_clock_tower

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.Prices
import com.yrc.pos.core.TicketsPrinting
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import kotlinx.android.synthetic.main.activity_enclosure_clock_tower_printing.*


class EnclosureClockTowerPrintingActivity : YrcBaseActivity() {
    private var countAdultTickets: Int = 0
    private var countOver65Tickets: Int = 0

    private var selectedButton: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enclosure_clock_tower_printing)
        val i = intent
        val bundle = i.extras
        if (bundle != null) {
            countAdultTickets = bundle.getInt(EnclosureClockTowerFragment.TICKET_ADULTS)
            countOver65Tickets = bundle.getInt(EnclosureClockTowerFragment.TICKET_OVER65)
        }

        button_adult.setOnClickListener {
            selectedButton = 1
        }
        button_over65.setOnClickListener {
            selectedButton = 2
        }
        button_1822.setOnClickListener {
            selectedButton = 3
        }
        button_racegoer.setOnClickListener {
            selectedButton = 4
        }
        button_cash.setOnClickListener {
            for (n in 1..countAdultTickets)
                TicketsPrinting.printAdultTicket(this)
            for (n in 1..countOver65Tickets)
                TicketsPrinting.printOver65Ticket(this)
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
                countAdultTickets -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_ADULTS,
                        countAdultTickets
                    )
                )
                updateUi()
            }
            2 -> {
                countOver65Tickets -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_OVER65,
                        countOver65Tickets
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
                countAdultTickets += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_ADULTS,
                        countAdultTickets
                    )
                )
                updateUi()
            }
            2 -> {
                countOver65Tickets += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureClockTowerFragment.TICKET_OVER65,
                        countOver65Tickets
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
    }

    fun onCardButtonClicked(view: View) {
        finish()
    }

    fun onCashButtonClicked(view: View) {
        finish()
    }

    private fun updateUi() {
        if (countAdultTickets != 0)
            button_adult.visibility = View.VISIBLE
        button_adult.text = countAdultTickets.toString().plus(" ")
            .plus("x Adult £".plus(" ").plus(countAdultTickets.times(Prices.PRICE_ADULT)))

        if (countOver65Tickets != 0)
            button_over65.visibility = View.VISIBLE
        button_over65.text = countOver65Tickets.toString().plus(" ")
            .plus("x Over65 £".plus(" ").plus(countOver65Tickets.times(Prices.PRICE_OVER65)))

        textView_ticket.text = countAdultTickets.plus(countOver65Tickets).toString().plus(" ")
            .plus(
                "x Ticket £".plus(" ")
                    .plus(
                        countAdultTickets.times(Prices.PRICE_ADULT)
                            .plus(countOver65Tickets.times(Prices.PRICE_OVER65))
                    )
            )

    }

}