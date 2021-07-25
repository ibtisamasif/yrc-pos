package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseFragment
import com.yrc.pos.core.providers.models.Ticket
import kotlinx.android.synthetic.main.fragment_enclosure_clock_tower.*
import java.util.*

class EnclosureClockTowerFragment : YrcBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_enclosure_clock_tower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdultButtonListener()
        setOver65ButtonListener()
        setTotalButtonListener()
    }

    override fun onResume() {
        super.onResume()
        try {
            button_Adult.text = "${TicketVM.originalTickets[0].name} £ ${TicketVM.originalTickets[0].price}"
            button_Over65.text = "${TicketVM.originalTickets[1].name} £ ${TicketVM.originalTickets[1].price}"
        } catch (e: Exception) {
        }
        button_total.text = TicketVM.getTotalText()
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            TicketVM.originalTickets[0].ticketPriceID?.let { it1 -> addTicketOrIncreaseCountIfItAlreadyExistsInList(it1) }
            moveToClockTowerCheckoutScreen()
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            TicketVM.originalTickets[1].ticketPriceID?.let { it1 -> addTicketOrIncreaseCountIfItAlreadyExistsInList(it1) }
            moveToClockTowerCheckoutScreen()
        }
    }

    private fun setTotalButtonListener() {
        button_total.setOnClickListener {
            moveToClockTowerCheckoutScreen()
        }
    }

    private fun addTicketOrIncreaseCountIfItAlreadyExistsInList(ticketPriceID: Int) {
        val matchingObjectInSelectedTicketList: Optional<Ticket> = TicketVM.selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList.isPresent) {
            matchingObjectInSelectedTicketList.get().quantity = matchingObjectInSelectedTicketList.get().quantity?.plus(1)
        } else {
            val matchingObjectInOriginalTicketList: Optional<Ticket> = TicketVM.originalTickets.stream().filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }.findFirst()
            if (matchingObjectInOriginalTicketList.isPresent) {
                val originalTicket = matchingObjectInOriginalTicketList.get()
                originalTicket.quantity = 1
                TicketVM.selectedTickets.add(originalTicket)
            }
        }
    }

    private fun moveToClockTowerCheckoutScreen() {
        startActivity(Intent(activity, EnclosureClockTowerPrintingActivity::class.java))
    }

}