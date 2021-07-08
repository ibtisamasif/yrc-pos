package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.TicketData
import com.yrc.pos.core.YrcBaseFragment
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_enclosure_clock_tower.*

class EnclosureClockTowerFragment : YrcBaseFragment() {

    private var button1Count = 0
    private var button2Count = 0

    private lateinit var disposableClearAllTickets: Disposable
    private lateinit var disposableMultiplyTicket: Disposable

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

        disposableClearAllTickets = RxBus.listen(RxEvent.buttonFunction::class.java).subscribe {
            button1Count = 0
            button2Count = 0
            Toast.makeText(activity, "Cleared all selections and reset", Toast.LENGTH_SHORT).show()
        }
        disposableMultiplyTicket = RxBus.listen(RxEvent.setTicketCount::class.java).subscribe {
            when (it.ticketName) {
                TICKET_ADULTS -> {
                    button1Count = it.count
                }
                TICKET_OVER65 -> {
                    button2Count = it.count
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val adultPrice = TicketData.originalTickets[0].price?.toDouble()?.toInt()
        val over65 = TicketData.originalTickets[1].price?.toDouble()?.toInt()
        button_total.text = button1Count.plus(button2Count).toString().plus(" ")
            .plus(
                "x Ticket £".plus(
                    adultPrice?.let {
                        over65?.let { it1 -> button2Count.times(it1) }?.let { it2 ->
                            button1Count.times(it)
                                .plus(it2)
                        }
                    }
                )
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposableClearAllTickets.isDisposed) disposableClearAllTickets.dispose()
        if (!disposableMultiplyTicket.isDisposed) disposableMultiplyTicket.dispose()
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            val intent = Intent(activity, EnclosureClockTowerPrintingActivity::class.java)
            button1Count += 1
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            startActivity(intent)
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            val intent = Intent(activity, EnclosureClockTowerPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            button2Count += 1
            intent.putExtra(TICKET_OVER65, button2Count)
            startActivity(intent)
        }
    }

    private fun setTotalButtonListener() {
        button_total.setOnClickListener {
            val intent = Intent(activity, EnclosureClockTowerPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        val TICKET_ADULTS = "ticket_adults"
        @JvmStatic
        val TICKET_OVER65 = "ticket_over65"
    }
}