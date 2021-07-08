package com.yrc.pos.features.enclosure_grand_stand

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.TicketData
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import com.yrc.pos.features.payment.views.PaymentActivity
import com.yrc.pos.features.voucher.views.VoucherActivity
import kotlinx.android.synthetic.main.activity_enclosure_grand_stand_printing.*


class EnclosureGrandStandPrintingActivity : YrcBaseActivity() {
    private var button1Count: Int = 0
    private var button2Count: Int = 0
    private var button3Count: Int = 0
    private var button4Count: Int = 0
    private var button5Count: Int = 0
    private var button6Count: Int = 0

    private var selectedButton: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enclosure_grand_stand_printing)
        val bundle = intent.extras
        if (bundle != null) {
            button1Count = bundle.getInt(EnclosureGrandStandFragment.TICKET_ADULTS)
            button2Count = bundle.getInt(EnclosureGrandStandFragment.TICKET_OVER65)
            button3Count = bundle.getInt(EnclosureGrandStandFragment.TICKET_1822)
            button4Count = bundle.getInt(EnclosureGrandStandFragment.TICKET_RACEGOER)
            button5Count = bundle.getInt(EnclosureGrandStandFragment.TICKET_BR_VOUCHER)
            button6Count = bundle.getInt(EnclosureGrandStandFragment.TICKET_U18S)
        }

        button_adult?.setOnClickListener {
            selectedButton = 1
            if (button_adult.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_1822?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_racegoer?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_BR_Voucher?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_U18s.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }
        button_over65?.setOnClickListener {
            selectedButton = 2
            if (button_over65.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_1822?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_racegoer?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_BR_Voucher?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_U18s.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }
        button_1822?.setOnClickListener {
            selectedButton = 3
            if (button_1822.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_1822?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_racegoer?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_BR_Voucher?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_U18s.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }
        button_racegoer?.setOnClickListener {
            selectedButton = 4
            if (button_racegoer.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_1822?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_racegoer?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_BR_Voucher?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_U18s.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }
        button_BR_Voucher?.setOnClickListener {
            selectedButton = 5
            if (button_BR_Voucher.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_1822?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_racegoer?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_BR_Voucher?.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
                button_U18s.setBackgroundColor(resources.getColor(R.color.colorWhite))
            }
        }
        button_U18s?.setOnClickListener {
            selectedButton = 6
            if (button_U18s.isPressed) {
                button_adult?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_over65?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_1822?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_racegoer?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_BR_Voucher?.setBackgroundColor(resources.getColor(R.color.colorWhite))
                button_U18s.setBackgroundColor(resources.getColor(R.color.colorGrayLight))
            }
        }
        button_cash?.setOnClickListener {
//            try {
//                for (n in 1..button1Count)
//                    TicketPrintUtils.printTicket(this, Prices.tickets[0].name, Prices.tickets[0].price)
//                for (n in 1..button2Count)
//                    TicketPrintUtils.printTicket(this, Prices.tickets[1].name, Prices.tickets[1].price)
//                for (n in 1..button3Count)
//                    TicketPrintUtils.printTicket(this, Prices.tickets[2].name, Prices.tickets[2].price)
//                for (n in 1..button4Count)
//                    TicketPrintUtils.printTicket(this, Prices.tickets[3].name, Prices.tickets[3].price)
//                for (n in 1..button5Count)
//                    TicketPrintUtils.printTicket(this, Prices.tickets[4].name, Prices.tickets[4].price)
//                for (n in 1..button6Count)
//                    TicketPrintUtils.printTicket(this, Prices.tickets[5].name, Prices.tickets[5].price)
//            } catch (ex: PrinterDevException) {
//                ex.printStackTrace()
//            }
        }
        updateUi()
    }

    fun onPlusButtonClicked(view: View) {
        onBackPressed()
    }

    fun onMinusButtonClicked(view: View) {
        when (selectedButton) {
            0 -> {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                if (button1Count > 0)
                    button1Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_ADULTS,
                        button1Count
                    )
                )
                updateUi()
            }
            2 -> {
                if (button2Count > 0)
                    button2Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_OVER65,
                        button2Count
                    )
                )
                updateUi()
            }
            3 -> {
                if (button3Count > 0)
                    button3Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_1822,
                        button3Count
                    )
                )
                updateUi()
            }
            4 -> {
                if (button4Count > 0)
                    button4Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_RACEGOER,
                        button4Count
                    )
                )
                updateUi()
            }
            5 -> {
                if (button5Count > 0)
                    button5Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_BR_VOUCHER,
                        button5Count
                    )
                )
                updateUi()
            }
            6 -> {
                if (button6Count > 0)
                    button6Count -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_U18S,
                        button6Count
                    )
                )
                updateUi()
            }
        }
    }

    fun onMultiplyButtonClicked(view: View) {
        when (selectedButton) {
            0 -> {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                button1Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_ADULTS,
                        button1Count
                    )
                )
                updateUi()
            }
            2 -> {
                button2Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_OVER65,
                        button2Count
                    )
                )
                updateUi()
            }
            3 -> {
                button3Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_1822,
                        button3Count
                    )
                )
                updateUi()
            }
            4 -> {
                button4Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_RACEGOER,
                        button4Count
                    )
                )
                updateUi()
            }
            5 -> {
                button5Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_BR_VOUCHER,
                        button5Count
                    )
                )
                updateUi()
            }
            6 -> {
                button6Count += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        EnclosureGrandStandFragment.TICKET_U18S,
                        button6Count
                    )
                )
                updateUi()
            }
        }
    }

    fun onCrossButtonClicked(view: View) {
        RxBus.publish(RxEvent.buttonFunction("onCrossButtonClicked"))
        finish()
    }

    fun onGVOldButtonClicked(view: View) {
    }

    fun onGVNewButtonClicked(view: View) {
        startActivity(Intent(this, VoucherActivity::class.java))
    }

    fun onCardButtonClicked(view: View) {
        startActivity(Intent(this, PaymentActivity::class.java))
    }

    fun onCashButtonClicked(view: View) {
        startActivity(Intent(this, PaymentActivity::class.java))
    }

    private fun updateUi() {
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
                                if (button1Count != 0)
                                    button_adult?.visibility = View.VISIBLE
                                button_adult?.text = button1Count.toString().plus(" ")
                                    .plus("x Adult £".plus(" ").plus(button1Count.times(adultPrice)))

                                if (button2Count != 0)
                                    button_over65?.visibility = View.VISIBLE
                                button_over65?.text = button2Count.toString().plus(" ")
                                    .plus("x Over65 £".plus(" ").plus(button2Count.times(over65Price)))

                                if (button3Count != 0)
                                    button_1822?.visibility = View.VISIBLE
                                button_1822?.text = button3Count.toString().plus(" ")
                                    .plus("x 18-22 £".plus(" ").plus(button3Count.times(price1822)))

                                if (button4Count != 0)
                                    button_racegoer?.visibility = View.VISIBLE
                                button_racegoer?.text = button4Count.toString().plus(" ")
                                    .plus("x Racegoer £".plus(" ").plus(button4Count.times(racegoerPrice)))

                                if (button5Count != 0)
                                    button_BR_Voucher?.visibility = View.VISIBLE
                                button_BR_Voucher?.text = button5Count.toString().plus(" ")
                                    .plus("x BR_Voucher £".plus(" ").plus(button5Count.times(brVoucherPrice)))

                                if (button6Count != 0)
                                    button_U18s?.visibility = View.VISIBLE
                                button_U18s?.text = button6Count.toString().plus(" ")
                                    .plus("x U18S £".plus(" ").plus(button6Count.times(u18sPrice)))

                                textView_ticket?.text = button1Count.plus(button2Count).plus(button3Count)
                                    .plus(button4Count).plus(button5Count).plus(button6Count).toString().plus(" ")
                                    .plus(
                                        "x Ticket £".plus(" ")
                                            .plus(
                                                button1Count.times(adultPrice)
                                                    .plus(button2Count.times(over65Price))
                                                    .plus(button3Count.times(price1822))
                                                    .plus(button4Count.times(racegoerPrice))
                                                    .plus(button5Count.times(brVoucherPrice))
                                                    .plus(button6Count.times(u18sPrice))
                                            )
                                    )
                            }
                        }
                    }
                }
            }
        }
    }

}