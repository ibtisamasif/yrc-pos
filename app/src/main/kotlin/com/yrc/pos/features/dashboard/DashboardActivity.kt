package com.yrc.pos.features.dashboard

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.providers.models.Ticket
import com.yrc.pos.features.checkout.CheckoutActivity
import com.yrc.pos.features.checkout.adapter.CheckoutTicketButtonAdapter
import com.yrc.pos.features.dashboard.adapter.MenuTicketButtonAdapter
import com.yrc.pos.features.dashboard.viewmodel.DashboardVM
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*

class DashboardActivity : YrcBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)
        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.header_background
            )
        )

        textView_race_day_title.text = DashboardVM.meetDayName
        textView_race_day_date.text = DashboardVM.meetDayDate
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        try {

            recyclerViewMenuTicketButtons.adapter =
                MenuTicketButtonAdapter(TicketVM.originalTickets) {
                    it.ticketPriceID?.let { it1 ->
                        addTicketOrIncreaseCountIfItAlreadyExistsInList(
                            it1
                        )
                        setTemporarySelection(it1)
                        moveToClockTowerCheckoutScreen()
                    }
                }

            button_total.text = TicketVM.getTotalText()
            button_total.setOnClickListener {
                moveToClockTowerCheckoutScreen()
            }

        } catch (e: Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addTicketOrIncreaseCountIfItAlreadyExistsInList(ticketPriceID: Int) {
        val matchingObjectInSelectedTicketList: Optional<Ticket> = TicketVM.selectedTickets.stream()
            .filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }.findFirst()
        if (matchingObjectInSelectedTicketList.isPresent) {
            matchingObjectInSelectedTicketList.get().quantity =
                matchingObjectInSelectedTicketList.get().quantity?.plus(1)
        } else {
            val matchingObjectInOriginalTicketList: Optional<Ticket> =
                TicketVM.originalTickets.stream()
                    .filter { p -> p.ticketPriceID?.equals(ticketPriceID) == true }.findFirst()
            if (matchingObjectInOriginalTicketList.isPresent) {
                val originalTicket = matchingObjectInOriginalTicketList.get()
                originalTicket.quantity = 1
                TicketVM.selectedTickets.add(originalTicket)
            }
        }
    }

    private fun setTemporarySelection(ticketId: Int) {
        TicketVM.selectedTickets.forEach {
            it.isTemporarySelected = it.ticketPriceID == ticketId
        }
    }

    private fun moveToClockTowerCheckoutScreen() {
        startActivity(Intent(this, CheckoutActivity::class.java))
    }

}