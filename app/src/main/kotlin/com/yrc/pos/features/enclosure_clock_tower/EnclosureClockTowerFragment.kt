package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yrc.pos.R
import com.yrc.pos.core.TicketData
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

        button_Adult.text = "${TicketData.originalTickets[0].name} £ ${TicketData.originalTickets[0].price}"
        button_Over65.text = "${TicketData.originalTickets[1].name} £ ${TicketData.originalTickets[1].price}"

        setAdultButtonListener()
        setOver65ButtonListener()
        setTotalButtonListener()
    }

    override fun onResume() {
        super.onResume()
        button_total.text = TicketData.getTotalText()
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            addTicketOrIncreaseCountIfItAlreadyExistsInList(33)
            moveToClockTowerCheckoutScreen()
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            addTicketOrIncreaseCountIfItAlreadyExistsInList(34)
            moveToClockTowerCheckoutScreen()
        }
    }

    private fun setTotalButtonListener() {
        button_total.setOnClickListener {
            moveToClockTowerCheckoutScreen()
        }
    }

    private fun addTicketOrIncreaseCountIfItAlreadyExistsInList(ticketPriceID: Int) {
        val matchingObjectInSelectedTicketList: Optional<Ticket> = TicketData.selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList.isPresent) {
            matchingObjectInSelectedTicketList.get().quantity = matchingObjectInSelectedTicketList.get().quantity?.plus(1)
        } else {
            val matchingObjectInOriginalTicketList: Optional<Ticket> = TicketData.originalTickets.stream().filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }.findFirst()
            if (matchingObjectInOriginalTicketList.isPresent) {
                val originalTicket = matchingObjectInOriginalTicketList.get()
                originalTicket.quantity = 1
                TicketData.selectedTickets.add(originalTicket)
            }
        }
    }

    private fun moveToClockTowerCheckoutScreen() {
        startActivity(Intent(activity, EnclosureClockTowerPrintingActivity::class.java))
    }

}