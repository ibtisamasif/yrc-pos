package com.yrc.pos.core

import android.os.Build
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.providers.models.Ticket
import java.util.*

object TicketVM {
    var deviceSerial: String = Build.SERIAL // Rob="0822163315" Ibt="0821180033"
    var enclosure: Enclosure = Enclosure.none
    var originalTickets = arrayListOf<Ticket>()
    var selectedTickets = arrayListOf<Ticket>()

    fun getTotalText(): String {
        var ticket1Quantity = 0
        var ticket2Quantity = 0
        var ticket1Price = 0
        var ticket2Price = 0

        val matchingObjectInSelectedTicketList1: Optional<Ticket> =
            selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(originalTickets[0].ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList1.isPresent) {
            val ticket1 = matchingObjectInSelectedTicketList1.get()
            ticket1Quantity = ticket1.quantity!!
            ticket1Price = ticket1.price?.toDouble()?.toInt()!!
        }

        val matchingObjectInSelectedTicketList2: Optional<Ticket> =
            selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(originalTickets[1].ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList2.isPresent) {
            val ticket2 = matchingObjectInSelectedTicketList2.get()
            ticket2Quantity = ticket2.quantity!!
            ticket2Price = ticket2.price?.toDouble()?.toInt()!!
        }

        return ticket1Quantity.plus(ticket2Quantity).toString().plus(" ")
            .plus(
                "x Ticket £".plus(" ")
                    .plus(
                        ticket1Quantity.times(ticket1Price)
                            .plus(ticket2Quantity.times(ticket2Price))
                    )
            )
    }

    fun getSubtotal(): Double {
        var ticket1Quantity = 0
        var ticket2Quantity = 0
        var ticket1Price = 0
        var ticket2Price = 0

        val matchingObjectInSelectedTicketList1: Optional<Ticket> =
            selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(originalTickets[0].ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList1.isPresent) {
            val ticket1 = matchingObjectInSelectedTicketList1.get()
            ticket1Quantity = ticket1.quantity!!
            ticket1Price = ticket1.price?.toDouble()?.toInt()!!
        }

        val matchingObjectInSelectedTicketList2: Optional<Ticket> =
            selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(originalTickets[1].ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList2.isPresent) {
            val ticket2 = matchingObjectInSelectedTicketList2.get()
            ticket2Quantity = ticket2.quantity!!
            ticket2Price = ticket2.price?.toDouble()?.toInt()!!
        }

        return ticket1Quantity.times(ticket1Price).plus(ticket2Quantity.times(ticket2Price))
            .toDouble()
    }

    fun getTicketByTicketPriceIdFromSelectedTicketList(ticketPriceID: Int): Ticket? {
        val matchingObjectInSelectedTicketList: Optional<Ticket> =
            selectedTickets.stream().filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }
                .findFirst()
        return if (matchingObjectInSelectedTicketList.isPresent) {
            matchingObjectInSelectedTicketList.get()
        } else {
            null
        }
    }

    fun clear() {
        selectedTickets.clear()
    }
}