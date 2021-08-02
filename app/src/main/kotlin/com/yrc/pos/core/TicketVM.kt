package com.yrc.pos.core

import android.os.Build
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.providers.models.Ticket
import java.text.DecimalFormat

object TicketVM {
    var deviceSerial: String = Build.SERIAL // Rob="0822163315" Ibt="0821180033"
//    var deviceSerial: String = Build.SERIAL // Rob="0822163315" Ibt="0821180033"
    var enclosure: Enclosure = Enclosure.none
    var originalTickets = arrayListOf<Ticket>()
    var selectedTickets = arrayListOf<Ticket>()

    fun getTotalText(): String {
        var totalQuantity = 0
        var totalPrice = 0.0
        selectedTickets.forEach {
            totalQuantity += it.quantity
            totalPrice += (it.price?.toDouble() ?: 0.0) * it.quantity
        }

        return totalQuantity.toString().plus(" ").plus("x Ticket £".plus(" ").plus(DecimalFormat("##.##").format(totalPrice)))
    }

    fun getSubtotal(): Double {
        var subtotal = 0.0
        selectedTickets.forEach {
            subtotal += (it.price?.toDouble() ?: 0.0) * it.quantity
        }
        return subtotal
    }

    fun clear() {
        selectedTickets.clear()
    }
}