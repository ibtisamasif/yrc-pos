package com.yrc.pos.features.enclosure_clock_tower.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yrc.pos.R
import com.yrc.pos.core.providers.models.Ticket

class MenuTicketButtonAdapter(
    private var list: List<Ticket>,
    private var onClick: (Ticket) -> Unit
) :
    RecyclerView.Adapter<MenuTicketButtonAdapter.MenuTicketViewHolder>() {

    class MenuTicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTicketNameAndPrice: TextView = view.findViewById(R.id.textViewTicketNameAndPrice)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuTicketViewHolder {
        return MenuTicketViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.menu_ticket_button_layout, viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: MenuTicketViewHolder, position: Int) {
        val ticket = list[position] as Ticket
        viewHolder.textViewTicketNameAndPrice.text = "${ticket.name} £ ${ticket.price}"
        viewHolder.itemView.setOnClickListener {
            onClick.invoke(ticket)
        }
    }

    override fun getItemCount() = list.size

    fun updateList(list: List<Ticket>) {
        this.list = list
        notifyDataSetChanged()
    }
}
