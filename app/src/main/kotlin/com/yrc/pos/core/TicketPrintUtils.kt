package com.yrc.pos.core

import android.content.Context
import android.graphics.BitmapFactory
import com.pax.dal.IDAL
import com.pax.dal.IPrinter
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.dal.exceptions.PrinterDevException
import com.pax.neptunelite.api.NeptuneLiteUser
import com.yrc.pos.R
import com.yrc.pos.core.providers.models.Ticket
import java.text.DateFormat
import java.util.*

object TicketPrintUtils {

    internal fun printTicket(context: Context, tickets: List<Ticket>) {
        tickets.forEach { oneTicket ->
            oneTicket.quantity?.let {

                for (i in 0..it) {

                    val dal: IDAL = NeptuneLiteUser.getInstance().getDal(context)
                    val prn = dal.printer
                    prn.init()

                    prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
                    prn.leftIndent(110)
//            prn.spaceSet(50.toByte(), 50.toByte())
                    prn.printStr(oneTicket.name, null)
                    prn.printStr("\n", null)
                    prn.leftIndent(100)

                    prn.printStr("£${oneTicket.price}", null)
                    prn.leftIndent(0)
                    prn.printStr("----------------", null)

                    prn.fontSet(EFontTypeAscii.FONT_16_32, EFontTypeExtCode.FONT_16_32)
                    prn.leftIndent(20)
                    prn.spaceSet(0.toByte(), 0.toByte())
                    prn.printStr(DateFormat.getDateTimeInstance().format(Date()), null)
                    prn.printStr("\n", null)

                    prn.leftIndent(20)
                    prn.printStr("Receipt only", null)
                    prn.printStr("\n", null)

                    prn.printStr("Not valid for entry", null)
                    prn.printStr("\n", null)

                    prn.fontSet(EFontTypeAscii.FONT_8_16, EFontTypeExtCode.FONT_16_16)
                    prn.leftIndent(20)
                    prn.dotLine
                    prn.printStr("Retain ticket as a proof", null)

                    prn.step(10)
                    prn.leftIndent(100)
//        prn.invert(true)
                    prn.printBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.qrcode))
                    prn.step(100)

                    startPrinting(dal)
                }
            }
        }
    }

    private fun startPrinting(dal: IDAL) {
        val apiResult: Int = dal.printer.start()
        try {
            when (apiResult) {
//                0 -> Toast.makeText(context, "Submission successfully made", Toast.LENGTH_SHORT).show()
//                1 -> Toast.makeText(context, "Busy, so far so good", Toast.LENGTH_SHORT).show()
//                2 -> Toast.makeText(context, "Out of paper", Toast.LENGTH_SHORT).show()
//                else -> Toast.makeText(context, "Unexpected", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: PrinterDevException) {
            ex.printStackTrace()
        }
    }

    private fun cutPaper(prn: IPrinter) {
        do { // Check every quarter-second for result of print. 
            Thread.sleep(250)
            val result = prn.status
        } while (result == 1)
        // Paper cutter. 
        try {
            val cutMode: Int = prn.cutMode
            if ((cutMode == 0) || (cutMode == 2)) {
                // 0=full, or 2=partial/full => full cut. 
                prn.cutPaper(0)
            } else if (cutMode == 1) {
                // 1=partial only => partial cut. 
                prn.cutPaper(1)
            }
        } catch (pdex: PrinterDevException) {
        }
    }
}