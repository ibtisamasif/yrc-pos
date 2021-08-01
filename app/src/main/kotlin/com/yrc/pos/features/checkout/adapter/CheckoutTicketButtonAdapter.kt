package com.yrc.pos.features.checkout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.providers.models.Ticket


class CheckoutTicketButtonAdapter(
    private var list: List<Ticket>
) :
    RecyclerView.Adapter<CheckoutTicketButtonAdapter.CheckoutTicketViewHolder>() {

    class CheckoutTicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTicket: TextView = view.findViewById(R.id.textViewTicket)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CheckoutTicketViewHolder {
        return CheckoutTicketViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.checkout_ticket_button_layout, viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: CheckoutTicketViewHolder, position: Int) {
        val ticket = list[position]
        if (ticket.isTemporarySelected) {
            viewHolder.textViewTicket.setBackgroundColor(viewHolder.itemView.resources.getColor(R.color.colorGrayLight))
        } else {
            viewHolder.textViewTicket.setBackgroundColor(viewHolder.itemView.resources.getColor(R.color.colorWhite))
        }

        viewHolder.textViewTicket.text = ticket.quantity.toString().plus(" ").plus(
            "x ${ticket.name} £".plus(" ").plus(ticket.quantity.times(ticket.price?.toDouble()!!))
        )

        viewHolder.itemView.setOnClickListener {
            ticket.ticketPriceID?.let { it1 -> setTemporarySelection(it1) }
            updateList(TicketVM.selectedTickets)
        }
    }

    override fun getItemCount() = list.size

     private fun setTemporarySelection(ticketId: Int) {
        TicketVM.selectedTickets.forEach {
            it.isTemporarySelected = it.ticketPriceID == ticketId
            notifyDataSetChanged()
        }
    }

    fun updateList(list: List<Ticket>) {
        this.list = list
        notifyDataSetChanged()
    }
}