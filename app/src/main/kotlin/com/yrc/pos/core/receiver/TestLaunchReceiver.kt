package com.yrc.pos.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.pax.dal.IPrinter
import com.pax.dal.exceptions.PrinterDevException
import com.pax.neptunelite.api.NeptuneLiteUser
import com.yrc.pos.core.TicketVM.deviceSerial
import com.yrc.pos.core.providers.models.YrcError
import com.yrc.pos.core.services.APiManager.postCompleteOrder
import com.yrc.pos.core.services.ApiCallbacks
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderRequest
import com.yrc.pos.features.order_successful.order_successful_service.CompleteOrderResponse
import com.yrc.pos.features.order_successful.views.OrderSuccessfulActivity
import eft.com.eftservicelib.EFTServiceLib
import eft.com.eftservicelib.EFTServiceTransResult
import eft.com.eftservicelib.HistoryTransResult
import java.io.Serializable
import java.util.*

/** */
/** */ /*
   This class provides the receiver that is declared in the manifest.
   It is used to receive the response to a transaction and debug the result.
   Once the result is received it brings this app back to the front with a call to startActivity
*/
/** */
/** */
class TestLaunchReceiver : BroadcastReceiver(), ApiCallbacks {
    var resultType: String? = null
    private val HISTORY_REPORTS = "History Reports"
    private val REPORTS = "Reports"
    private var mContext: Context? = null
    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        //        if (TRANSACTION_RECEIPT_EVENT.equals(intent.getAction())) {
//
//            Bitmap merchant = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("ReceiptDataMerchant"), 0, intent.getByteArrayExtra("ReceiptDataMerchant").length);
//            Bitmap cardholder = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("ReceiptDataCardholder"), 0, intent.getByteArrayExtra("ReceiptDataCardholder").length);
//
//            //Bitmap merchant = intent.getParcelableExtra("ReceiptDataMerchant");
//            //Bitmap cardholder = intent.getParcelableExtra("ReceiptDataCardholder");
//            printBitmap(context, merchant);
//            printBitmap(context, cardholder);
//        }
        if (TRANS_IN_BATCH_RESPONSE_EVENT == intent.action) {
            Log.i(TAG, "TransInBatch :" + intent.getIntExtra("TransInBatch", 0))
        } else if (TRANSACTION_RESULT_EVENT == intent.action) {
            /* unpack the transaction result */
            /* this will return null of the event was not for us, or if there was an error */
            if (intent.hasExtra("ReceiverResultType")) {
                resultType = intent.getStringExtra("ReceiverResultType")
                if (resultType != null && resultType == HISTORY_REPORTS) {
                    displayHistoryLogs(intent)
                } else if (resultType != null && resultType == REPORTS) {
                    displayReportLogs(intent)
                } else if (resultType != null && resultType == "Submit Trans") {
                    Log.i(
                        TAG,
                        "Is batch upload successful = " + intent.getBooleanExtra(
                            "IsSuccessful",
                            true
                        )
                    )
                    Log.i(
                        TAG,
                        "Number of transactions uploaded :" + intent.getIntExtra("BatchCount", 0)
                    )
                }
            } else {
                val result = EFTServiceLib.unpackResult(context, intent)
                if (result != null) {
                    Log.i(TAG, "Using EFTServiceLib version:" + EFTServiceLib.getVersion())
                    val transFound = intent.getBooleanExtra("TransResponse", false)
                    if (transFound) {
                        /* Unpack the transaction results */
                        val rrn = result.rrn
                        val approved = result.isApproved
                        val receiptNumber = result.receiptNumber

                        /* Debug the result */Log.i(TAG, "Transaction Type = " + result.transType)
                        if (intent.hasExtra("UTI")) //                            OrderSuccessfulActivity.lastReceivedUTI = intent.getStringExtra("UTI");
                            Log.i(TAG, "Amount = " + result.amount)
                        Log.i(TAG, "Gratuity = " + intent.getLongExtra("Tip", 0))
                        Log.i(TAG, "MsgStatus= " + intent.getStringExtra("MsgStatus"))

                        /* all the additional extra data you can get */Log.i(
                            TAG,
                            "Approved = " + intent.getBooleanExtra("Approved", false)
                        )
                        Log.i(TAG, "Cancelled = " + intent.getBooleanExtra("Cancelled", false))
                        Log.i(
                            TAG,
                            "LastStatus(FOR ADDITIONAL INFO ONLY) = " + intent.getStringExtra("LastStatus")
                        )
                        Log.i(TAG, "SigRequired = " + intent.getBooleanExtra("SigRequired", false))
                        Log.i(TAG, "PINVerified = " + intent.getBooleanExtra("PINVerified", false))
                        Log.i(TAG, "AuthMode = " + intent.getStringExtra("AuthMode"))
                        Log.i(TAG, "Currency = " + intent.getStringExtra("Currency"))
                        Log.i(TAG, "Tid = " + intent.getStringExtra("Tid"))
                        Log.i(TAG, "Mid = " + intent.getStringExtra("Mid"))
                        Log.i(TAG, "Version = " + intent.getStringExtra("Version"))
                        Log.i(TAG, "Reference = " + result.reference)
                        if (intent.hasExtra("TestConnectStatus")) Log.i(
                            TAG,
                            "TestConnectStatus = " + intent.getStringExtra("TestConnectStatus")
                        )
                        if (intent.getBooleanExtra("TransactionDetails", false)) {
                            Log.i(TAG, "Transaction Details:")
                            Log.i(
                                TAG,
                                "FlightReference = " + intent.getStringExtra("FlightReference")
                            )
                            Log.i(TAG, "ReceiptNumber = " + intent.getIntExtra("ReceiptNumber", 0))
                            Log.i(TAG, "RRN = " + intent.getStringExtra("RRN"))
                            Log.i(TAG, "ResponseCode = " + intent.getStringExtra("ResponseCode"))
                            Log.i(TAG, "Stan = " + intent.getIntExtra("Stan", 0))
                            Log.i(TAG, "AuthCode = " + intent.getStringExtra("AuthCode"))
                            Log.i(
                                TAG,
                                "MerchantTokenId = " + intent.getStringExtra("MerchantTokenId")
                            )
                            if (intent.hasExtra("CardType")) {
                                val cardType = intent.getStringExtra("CardType")
                                Log.i(TAG, "CardType = $cardType")
                                if (cardType!!.compareTo("EMV") == 0 || cardType.compareTo("CTLS") == 0) {
                                    Log.i(TAG, "AID = " + intent.getStringExtra("AID"))
                                    Log.i(TAG, "TSI = " + intent.getStringExtra("TSI"))
                                    Log.i(TAG, "TVR = " + intent.getStringExtra("TVR"))
                                    Log.i(
                                        TAG,
                                        "CardHolder = " + intent.getStringExtra("CardHolder")
                                    )
                                    Log.i(
                                        TAG,
                                        "Cryptogram = " + byteArrayToHexString(
                                            intent.getByteArrayExtra("Cryptogram")
                                        )
                                    )
                                    Log.i(
                                        TAG,
                                        "CryptogramType = " + intent.getStringExtra("CryptogramType")
                                    )
                                }
                            }
                            if (intent.getBooleanExtra("IsVoucherUsed", false)) {
                                Log.i(
                                    TAG,
                                    "Voucher Code Used = " + intent.getStringExtra("VoucherCodeUsed")
                                )
                                Log.i(
                                    TAG,
                                    "Value Of Voucher = " + intent.getLongExtra("ValueOfVoucher", 0)
                                )
                            }
                        } else {
                            Log.i(TAG, "Transaction not found")
                        }
                        bringToFront(context, result, intent)
                    }
                }
            }
        }
    }

    private fun displayReportLogs(intent: Intent?) {
        if (intent != null) {
            Log.d(TAG, "Report Type = " + intent.getStringExtra("ReportType"))
            Log.d(TAG, "Sale Count = " + intent.getLongExtra("SaleCount", 0))
            Log.d(TAG, "Sale Amount = " + intent.getLongExtra("SaleAmount", 0) / 100.0)
            Log.d(TAG, "Refund Count = " + intent.getLongExtra("RefundCount", 0))
            Log.d(TAG, "Refund Amount = " + intent.getLongExtra("RefundAmount", 0) / 100.0)
            Log.d(TAG, "Cashback Count = " + intent.getLongExtra("CashbackCount", 0))
            Log.d(TAG, "CashBack Amount = " + intent.getLongExtra("CashbackAmount", 0) / 100.0)
            Log.d(TAG, "Gratuity Count= " + intent.getLongExtra("GratuityCount", 0))
            Log.d(TAG, "Gratuity Amount = " + intent.getLongExtra("GratuityAmount", 0) / 100.0)
            Log.d(TAG, "Voucher Count= " + intent.getLongExtra("VoucherCount", 0))
            Log.d(TAG, "Voucher Amount = " + intent.getLongExtra("VoucherAmount", 0) / 100.0)
        }
    }

    private fun displayHistoryLogs(intent: Intent) {
        val transHistoryList: ArrayList<HistoryTransResult>? =
            intent.getParcelableArrayListExtra("HistoryList")
        if (transHistoryList != null) {
            for (historyItem in transHistoryList) {
                Log.d(TAG, "TransType = " + historyItem.transType)
                Log.d(TAG, "Amount = " + historyItem.transAmount / 100)
                Log.d(TAG, "Currency = " + historyItem.currency)
                Log.d(TAG, "Status = " + historyItem.transApproved)
                Log.d(TAG, "Date Time = " + historyItem.transDate)
                Log.d(TAG, "PAN = " + historyItem.transPan)
                Log.d(TAG, "RRN = " + historyItem.rnn)
                Log.d(TAG, "Receipt No = " + historyItem.receiptNo)
                Log.d(TAG, "PID = " + historyItem.pid)
                Log.d(TAG, "CVM method = " + historyItem.cvmMethod)
                Log.d(TAG, "------------------------------------------------------------")
            }
        }
    }

    private fun bringToFront(context: Context, reference: EFTServiceTransResult, intent: Intent) {
        /* Bring this application back into the foreground */
        when {
            intent.getBooleanExtra("Approved", false) -> {
                postCompleteOrder(
                    context,
                    this,
                    CompleteOrderRequest(deviceSerial, reference.reference.toInt(), "PAID")
                )
            }
            intent.getBooleanExtra("Cancelled", false) -> {
                postCompleteOrder(
                    context,
                    this,
                    CompleteOrderRequest(deviceSerial, reference.reference.toInt(), "ERROR")
                )
            }
            else -> {
                Toast.makeText(
                    context,
                    "Anonymous state. Transaction was neither Approved nor Cancelled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun byteArrayToHexString(byteArray: ByteArray?): String {
        if (byteArray == null) {
            return ""
            //throw new IllegalArgumentException("Argument 'byteArray' cannot be null");
        }
        val readBytes = byteArray.size
        val hexData = StringBuilder()
        var onebyte: Int
        for (b in byteArray) {
            onebyte = 0x000000ff and b.toInt() or -0x100
            hexData.append(Integer.toHexString(onebyte).substring(6))
        }
        return hexData.toString().toUpperCase()
    }

    private fun start(printer: IPrinter): Int {
        try {
            while (true) {
                val ret = printer.start()
                // printer is busy, please wait
                if (ret == 1) {
                    sleep(1000)
                    continue
                } else if (ret == 2) {
                    Log.i(TAG, "Printer is Out of Paper")
                    return -1
                } else if (ret == 8) {
                    Log.i(TAG, "Printer is too hot")
                    return -1
                } else if (ret == 9) {
                    Log.i(TAG, "Voltage is too low!")
                    return -1
                } else if (ret != 0) {
                    return -1
                }
                return 0
            }
        } catch (e: PrinterDevException) {
            e.printStackTrace()
            return 0
        }
    }

    private fun getPrinterStatus(iPaxPrinter: IPrinter): Int {
        try {
            var status = iPaxPrinter.status
            if (status == -4) { // Pax library error returned -4 (font not installed) when it shouldn't (raised a case with pax)
                status = psSuccess
            }
            return status
        } catch (ex: Exception) {
        }
        return psFailureUnknown
    }

    fun printBitmap(context: Context?, printable: Bitmap?): Int {
        var status = psSuccess
        try {
            val iPaxDal = NeptuneLiteUser.getInstance().getDal(context)
            val iPaxPrinter = iPaxDal.printer
            iPaxPrinter.init()
            iPaxPrinter.setGray(4)
            iPaxPrinter.printBitmap(printable)
            /*Spool Out the receipt*/iPaxPrinter.step(150)
            start(iPaxPrinter)
            /*Wait for the Printing to Occur*/do {
                status = getPrinterStatus(iPaxPrinter)
                if (status == psBusy) {
                    /*Printer is doing its job*/
                    Thread.sleep(200)
                } else {
                    break
                }
            } while (status != psSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return status
    }

    override fun doBeforeApiCall() {}
    override fun doAfterApiCall() {}
    override fun onApiFailure(errorCode: YrcError) {
        moveToOrderSuccessfulScreen()
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        (apiResponse as? CompleteOrderResponse)?.let { response ->
            apiResponse.orderId?.let {
                moveToOrderSuccessfulScreen(response)
            } ?: run {
                moveToOrderSuccessfulScreen()
            }
        }
    }

    private fun moveToOrderSuccessfulScreen(apiResponse: YrcBaseApiResponse? = null) {
        mContext?.let { context ->
            Log.i(TAG, "Bring OrderSuccessfulActivity to the front")
            val notificationIntent = Intent(context, OrderSuccessfulActivity::class.java)
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            apiResponse?.let {
                notificationIntent.putExtra(
                    OrderSuccessfulActivity.COMPLETE_ORDER_RESPONSE,
                    it as Serializable
                )
            }
            context.startActivity(notificationIntent)
        }
    }

    companion object {
        const val TRANSACTION_RESULT_EVENT = "eft.com.TRANSACTION_RESULT"
        const val TRANSACTION_RECEIPT_EVENT = "eft.com.TRANSACTION_RECEIPT_EVENT"
        const val TRANS_IN_BATCH_RESPONSE_EVENT = "eft.com.TRANS_IN_BATCH_RESPONSE_EVENT"

        /* These need to match up with the manifest for app and the service (DOP NOT CHANGE) */
        private const val TAG = "TestLaunchReceiver"
        fun sleep(iTimeoutMS: Int) {
            try {
                Thread.sleep(iTimeoutMS.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        var psFailureUnknown = 99999 // A random high Value for now
        var psSuccess = 0
        var psBusy = 1
    }
}