package com.yrc.pos.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeResource
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.pax.dal.IDAL
import com.pax.dal.IPrinter
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.dal.exceptions.PrinterDevException
import com.pax.neptunelite.api.NeptuneLiteUser
import com.yrc.pos.R
import com.yrc.pos.core.providers.models.Ticket
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.util.*

object TicketPrintUtils {

    internal fun printTicket(
        context: Context,
        tickets: List<Ticket>,
        orderId: Int,
        qrCode: List<String>
    ) {
        var count = 0
        tickets.forEach { oneTicket ->
            for (i in 1..oneTicket.quantity) {
                val dal: IDAL = NeptuneLiteUser.getInstance().getDal(context)
                val prn = dal.printer
                prn.init()
                prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
                prn.leftIndent(50)
                prn.printBitmapWithMonoThreshold(decodeResource(context.resources, R.drawable.logo),1)
                prn.printStr("\n", null)
                prn.leftIndent(50)

                prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
                prn.leftIndent(110)
                prn.printStr(oneTicket.name, null)
                prn.printStr("\n", null)
                prn.leftIndent(100)

                prn.printStr("£${oneTicket.price}", null)
                prn.leftIndent(0)
                prn.printStr("----------------", null)

                prn.fontSet(EFontTypeAscii.FONT_16_32, EFontTypeExtCode.FONT_16_32)
                prn.leftIndent(30)
                prn.spaceSet(0.toByte(), 0.toByte())
                prn.printStr(DateFormat.getDateTimeInstance().format(Date()), null)
                prn.printStr("\n", null)

                prn.leftIndent(30)
                prn.printStr(context.resources.getString(R.string.order_successful, orderId), null)
                prn.printStr("\n", null)

                prn.fontSet(EFontTypeAscii.FONT_8_16, EFontTypeExtCode.FONT_16_16)
                prn.leftIndent(30)
                prn.dotLine
                prn.printStr("Retain ticket as a proof", null)

                prn.step(10)
                prn.leftIndent(90)
                //        prn.invert(true)
                try {
                    prn.printBitmap(bitmapToPng(qrCode[count]))
                    prn.step(100)
                    startPrinting(dal)
                    if (count < qrCode.size) {
                        count++
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    private fun bitmapToPng(qrCode: String?): Bitmap {
        val qrgEncoder = QRGEncoder(qrCode, null, QRGContents.Type.TEXT, 200)
        lateinit var bitmap: Bitmap
        try {
            bitmap = qrgEncoder.bitmap
            return codec(bitmap, Bitmap.CompressFormat.PNG, 100)
        } catch (e: PrinterDevException) {
            e.printStackTrace()
        }
        return bitmap
    }

    private fun codec(
        src: Bitmap, format: Bitmap.CompressFormat, quality: Int
    ): Bitmap {
        val os = ByteArrayOutputStream()
        src.compress(format, quality, os)
        val array: ByteArray = os.toByteArray()
        return BitmapFactory.decodeByteArray(array, 0, array.size)
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