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
import com.yrc.pos.features.enclosure_clock_tower.adapter.MenuTicketButtonAdapter
import kotlinx.android.synthetic.main.fragment_enclosure_clock_tower.*
import java.util.*

class EnclosureClockTowerFragment : YrcBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_enclosure_clock_tower, container, false)
    }

    override fun onResume() {
        super.onResume()
        try {

            recyclerViewMenuButtons.adapter = MenuTicketButtonAdapter(TicketVM.originalTickets) {
                it.ticketPriceID?.let { it1 -> addTicketOrIncreaseCountIfItAlreadyExistsInList(it1) }
                moveToClockTowerCheckoutScreen()
            }

            button_total.text = TicketVM.getTotalText()
            button_total.setOnClickListener {
                moveToClockTowerCheckoutScreen()
            }

        } catch (e: Exception) {
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