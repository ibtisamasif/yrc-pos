package com.yrc.pos.features.enclosure_grand_stand

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.fragment_enclosure_grand_stand.*

class EnclosureGrandStandFragment : YrcBaseFragment() {

    private var button1Count = 0
    private var button2Count = 0
    private var button3Count = 0
    private var button4Count = 0
    private var button5Count = 0
    private var button6Count = 0

    private lateinit var disposableClearAllTickets: Disposable
    private lateinit var disposableMultiplyTicket: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_enclosure_grand_stand, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_Adult?.text = "${TicketData.originalTickets[0].name} £ ${TicketData.originalTickets[0].price}"
        button_1822?.text = "${TicketData.originalTickets[1].name} £ ${TicketData.originalTickets[1].price}"
        button_Over65?.text = "${TicketData.originalTickets[2].name} £ ${TicketData.originalTickets[2].price}"
        button_racegoer?.text = "${TicketData.originalTickets[3].name} £ ${TicketData.originalTickets[3].price}"
        button_BR_Voucher?.text = "${TicketData.originalTickets[4].name} £ ${TicketData.originalTickets[4].price}"
        button_U18s?.text = "${TicketData.originalTickets[5].name} £ ${TicketData.originalTickets[5].price}"

        setAdultButtonListener()
        setOver65ButtonListener()
        set1822ButtonListener()
        setRacegoerButtonListener()
        setBRVoucherButtonListener()
        setU18sButtonListener()
        setTotalButtonListener()

        disposableClearAllTickets = RxBus.listen(RxEvent.buttonFunction::class.java).subscribe {
            button1Count = 0
            button2Count = 0
            button3Count = 0
            button4Count = 0
            button5Count = 0
            button6Count = 0
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
                TICKET_1822 -> {
                    button3Count = it.count
                }
                TICKET_RACEGOER -> {
                    button4Count = it.count
                }
                TICKET_BR_VOUCHER -> {
                    button5Count = it.count
                }
                TICKET_U18S -> {
                    button6Count = it.count
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val adult = TicketData.originalTickets[0].price?.toDouble()?.toInt()
        val price1822 = TicketData.originalTickets[1].price?.toDouble()?.toInt()
        val over65 = TicketData.originalTickets[2].price?.toDouble()?.toInt()
        val racegoer = TicketData.originalTickets[3].price?.toDouble()?.toInt()
        val brVoucher = TicketData.originalTickets[4].price?.toDouble()?.toInt()
        val u18s = TicketData.originalTickets[5].price?.toDouble()?.toInt()
        adult?.let { adultPrice ->
            price1822?.let { price1822 ->
                over65?.let { over65Price ->
                    racegoer?.let { racegoerPrice ->
                        brVoucher?.let { brVoucherPrice ->
                            u18s?.let { u18sPrice ->
                                button_total?.text = button1Count.plus(button2Count).plus(button3Count)
                                    .plus(button4Count).plus(button5Count).plus(button6Count).toString().plus(" ")
                                    .plus(
                                        "x Ticket £".plus(
                                            button1Count.times(adultPrice)
                                                .plus(button2Count.times(over65Price))
                                                .plus(button3Count.times(price1822)
                                                    .plus(button4Count.times(racegoerPrice))
                                                    .plus(button5Count.times(brVoucherPrice))
                                                    .plus(button6Count.times(u18sPrice))
                                                )
                                        )
                                    )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposableClearAllTickets.isDisposed) disposableClearAllTickets.dispose()
        if (!disposableMultiplyTicket.isDisposed) disposableMultiplyTicket.dispose()
    }

    private fun setAdultButtonListener() {
        button_Adult?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            button1Count += 1
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            intent.putExtra(TICKET_1822, button3Count)
            intent.putExtra(TICKET_RACEGOER, button4Count)
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            button2Count += 1
            intent.putExtra(TICKET_OVER65, button2Count)
            intent.putExtra(TICKET_1822, button3Count)
            intent.putExtra(TICKET_RACEGOER, button4Count)
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    private fun set1822ButtonListener() {
        button_1822?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            button3Count += 1
            intent.putExtra(TICKET_1822, button3Count)
            intent.putExtra(TICKET_RACEGOER, button4Count)
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    private fun setRacegoerButtonListener() {
        button_racegoer?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            intent.putExtra(TICKET_1822, button3Count)
            button4Count += 1
            intent.putExtra(TICKET_RACEGOER, button4Count)
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    private fun setBRVoucherButtonListener() {
        button_BR_Voucher?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            intent.putExtra(TICKET_1822, button3Count)
            intent.putExtra(TICKET_RACEGOER, button4Count)
            button5Count += 1
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    private fun setU18sButtonListener() {
        button_U18s?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            intent.putExtra(TICKET_1822, button3Count)
            intent.putExtra(TICKET_RACEGOER, button4Count)
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            button6Count += 1
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    private fun setTotalButtonListener() {
        button_total?.setOnClickListener {
            val intent = Intent(activity, EnclosureGrandStandPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, button1Count)
            intent.putExtra(TICKET_OVER65, button2Count)
            intent.putExtra(TICKET_1822, button3Count)
            intent.putExtra(TICKET_RACEGOER, button4Count)
            intent.putExtra(TICKET_BR_VOUCHER, button5Count)
            intent.putExtra(TICKET_U18S, button6Count)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        val TICKET_ADULTS = "ticket_adults"
        @JvmStatic
        val TICKET_OVER65 = "ticket_over65"
        @JvmStatic
        val TICKET_1822 = "ticket_1822"
        @JvmStatic
        val TICKET_RACEGOER = "ticket_racegoer"
        @JvmStatic
        val TICKET_BR_VOUCHER = "ticket_br_voucher"
        @JvmStatic
        val TICKET_U18S = "ticket_u18s"
    }
}