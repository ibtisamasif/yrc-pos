package com.yrc.pos.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pax.dal.IDAL;
import com.pax.dal.IPrinter;
import com.pax.dal.exceptions.PrinterDevException;
import com.pax.neptunelite.api.NeptuneLiteUser;
import com.yrc.pos.features.order_successful.views.OrderSuccessfulActivity;

import java.util.ArrayList;

import eft.com.eftservicelib.EFTServiceLib;
import eft.com.eftservicelib.EFTServiceTransResult;
import eft.com.eftservicelib.HistoryTransResult;

/**************************************************************************************************/
/**************************************************************************************************/

/*
   This class provides the receiver that is declared in the manifest.
   It is used to receive the response to a transaction and debug the result.
   Once the result is received it brings this app back to the front with a call to startActivity
*/

/**************************************************************************************************/

/**************************************************************************************************/
public class TestLaunchReceiver extends BroadcastReceiver {

    public static final String TRANSACTION_RESULT_EVENT = "eft.com.TRANSACTION_RESULT";
    public static final String TRANSACTION_RECEIPT_EVENT = "eft.com.TRANSACTION_RECEIPT_EVENT";
    public static final String TRANS_IN_BATCH_RESPONSE_EVENT = "eft.com.TRANS_IN_BATCH_RESPONSE_EVENT";

    /* These need to match up with the manifest for app and the service (DOP NOT CHANGE) */
    private static final String TAG = "TestLaunchReceiver";
    String resultType;
    private final String HISTORY_REPORTS = "History Reports";
    private final String REPORTS = "Reports";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TRANSACTION_RECEIPT_EVENT.equals(intent.getAction())) {

            Bitmap merchant = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("ReceiptDataMerchant"), 0, intent.getByteArrayExtra("ReceiptDataMerchant").length);
            Bitmap cardholder = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("ReceiptDataCardholder"), 0, intent.getByteArrayExtra("ReceiptDataCardholder").length);

            //Bitmap merchant = intent.getParcelableExtra("ReceiptDataMerchant");
            //Bitmap cardholder = intent.getParcelableExtra("ReceiptDataCardholder");
            printBitmap(context, merchant);
            printBitmap(context, cardholder);
        }

        if (TRANS_IN_BATCH_RESPONSE_EVENT.equals(intent.getAction())) {
            if (intent != null) {
                Log.i(TAG, "TransInBatch :" + intent.getIntExtra("TransInBatch", 0));
            }
        } else if (TRANSACTION_RESULT_EVENT.equals(intent.getAction())) {
            /* unpack the transaction result */
            /* this will return null of the event was not for us, or if there was an error */
            if (intent.hasExtra("ReceiverResultType")) {
                resultType = intent.getStringExtra("ReceiverResultType");

                if (resultType != null && resultType.equals(HISTORY_REPORTS)) {
                    DisplayHistoryLogs(intent);
                } else if (resultType != null && resultType.equals(REPORTS)) {
                    displayReportLogs(intent);
                } else if (resultType != null && resultType.equals("Submit Trans")) {
                    Log.i(TAG, "Is batch upload successful = " + intent.getBooleanExtra("IsSuccessful", true));
                    Log.i(TAG, "Number of transactions uploaded :" + intent.getIntExtra("BatchCount", 0));
                }
            } else {
                EFTServiceTransResult result = EFTServiceLib.unpackResult(context, intent);

                if (result != null) {

                    Log.i(TAG, "Using EFTServiceLib version:" + EFTServiceLib.getVersion());

                    boolean transFound = intent.getBooleanExtra("TransResponse", false);

                    if (transFound) {
                        /* Unpack the transaction results */
                        String rrn = result.getRrn();
                        boolean approved = result.isApproved();
                        Integer receiptNumber = result.getReceiptNumber();

                        /* Debug the result */
                        Log.i(TAG, "Transaction Type = " + result.getTransType());
                        if (intent.hasExtra("UTI"))
//                            OrderSuccessfulActivity.lastReceivedUTI = intent.getStringExtra("UTI");


                            Log.i(TAG, "Amount = " + result.getAmount());
                        Log.i(TAG, "Gratuity = " + intent.getLongExtra("Tip", 0));
                        Log.i(TAG, "MsgStatus= " + intent.getStringExtra("MsgStatus"));

                        /* all the additional extra data you can get */
                        Log.i(TAG, "Approved = " + intent.getBooleanExtra("Approved", false));
                        Log.i(TAG, "Cancelled = " + intent.getBooleanExtra("Cancelled", false));
                        Log.i(TAG, "LastStatus(FOR ADDITIONAL INFO ONLY) = " + intent.getStringExtra("LastStatus"));
                        Log.i(TAG, "SigRequired = " + intent.getBooleanExtra("SigRequired", false));
                        Log.i(TAG, "PINVerified = " + intent.getBooleanExtra("PINVerified", false));
                        Log.i(TAG, "AuthMode = " + intent.getStringExtra("AuthMode"));
                        Log.i(TAG, "Currency = " + intent.getStringExtra("Currency"));
                        Log.i(TAG, "Tid = " + intent.getStringExtra("Tid"));
                        Log.i(TAG, "Mid = " + intent.getStringExtra("Mid"));
                        Log.i(TAG, "Version = " + intent.getStringExtra("Version"));
                        Log.i(TAG, "Reference = " + result.getReference());
                        if (intent.hasExtra("TestConnectStatus"))
                            Log.i(TAG, "TestConnectStatus = " + intent.getStringExtra("TestConnectStatus"));

                        if (intent.getBooleanExtra("TransactionDetails", false)) {
                            Log.i(TAG, "Transaction Details:");
                            Log.i(TAG, "FlightReference = " + intent.getStringExtra("FlightReference"));
                            Log.i(TAG, "ReceiptNumber = " + intent.getIntExtra("ReceiptNumber", 0));
                            Log.i(TAG, "RRN = " + intent.getStringExtra("RRN"));
                            Log.i(TAG, "ResponseCode = " + intent.getStringExtra("ResponseCode"));
                            Log.i(TAG, "Stan = " + intent.getIntExtra("Stan", 0));
                            Log.i(TAG, "AuthCode = " + intent.getStringExtra("AuthCode"));
                            Log.i(TAG, "MerchantTokenId = " + intent.getStringExtra("MerchantTokenId"));

                            if (intent.hasExtra("CardType")) {

                                String cardType = intent.getStringExtra("CardType");
                                Log.i(TAG, "CardType = " + cardType);

                                if (cardType.compareTo("EMV") == 0 || cardType.compareTo("CTLS") == 0) {

                                    Log.i(TAG, "AID = " + intent.getStringExtra("AID"));
                                    Log.i(TAG, "TSI = " + intent.getStringExtra("TSI"));
                                    Log.i(TAG, "TVR = " + intent.getStringExtra("TVR"));
                                    Log.i(TAG, "CardHolder = " + intent.getStringExtra("CardHolder"));
                                    Log.i(TAG, "Cryptogram = " + byteArrayToHexString(intent.getByteArrayExtra("Cryptogram")));
                                    Log.i(TAG, "CryptogramType = " + intent.getStringExtra("CryptogramType"));

                                }
                            }

                            if (intent.getBooleanExtra("IsVoucherUsed", false)) {
                                Log.i(TAG, "Voucher Code Used = " + intent.getStringExtra("VoucherCodeUsed"));
                                Log.i(TAG, "Value Of Voucher = " + intent.getLongExtra("ValueOfVoucher", 0));
                            }
                        } else {
                            Log.i(TAG, "Transaction not found");
                        }
//                        bringToFront(context);
                    }
                }
            }
        }
    }

    private void displayReportLogs(Intent intent) {
        if (intent != null) {
            Log.d(TAG, "Report Type = " + intent.getStringExtra("ReportType"));
            Log.d(TAG, "Sale Count = " + intent.getLongExtra("SaleCount", 0));
            Log.d(TAG, "Sale Amount = " + intent.getLongExtra("SaleAmount", 0) / 100.0);
            Log.d(TAG, "Refund Count = " + intent.getLongExtra("RefundCount", 0));
            Log.d(TAG, "Refund Amount = " + intent.getLongExtra("RefundAmount", 0) / 100.0);
            Log.d(TAG, "Cashback Count = " + intent.getLongExtra("CashbackCount", 0));
            Log.d(TAG, "CashBack Amount = " + intent.getLongExtra("CashbackAmount", 0) / 100.0);
            Log.d(TAG, "Gratuity Count= " + intent.getLongExtra("GratuityCount", 0));
            Log.d(TAG, "Gratuity Amount = " + intent.getLongExtra("GratuityAmount", 0) / 100.0);
            Log.d(TAG, "Voucher Count= " + intent.getLongExtra("VoucherCount", 0));
            Log.d(TAG, "Voucher Amount = " + intent.getLongExtra("VoucherAmount", 0) / 100.0);

        }
    }


    private void DisplayHistoryLogs(Intent intent) {

        ArrayList<HistoryTransResult> transHistoryList = intent.getParcelableArrayListExtra("HistoryList");
        if (transHistoryList != null) {
            for (HistoryTransResult historyItem : transHistoryList) {

                Log.d(TAG, "TransType = " + historyItem.getTransType());
                Log.d(TAG, "Amount = " + historyItem.getTransAmount() / 100);
                Log.d(TAG, "Currency = " + historyItem.getCurrency());
                Log.d(TAG, "Status = " + historyItem.getTransApproved());
                Log.d(TAG, "Date Time = " + historyItem.getTransDate());
                Log.d(TAG, "PAN = " + historyItem.getTransPan());
                Log.d(TAG, "RRN = " + historyItem.getRnn());
                Log.d(TAG, "Receipt No = " + historyItem.getReceiptNo());
                Log.d(TAG, "PID = " + historyItem.getPid());
                Log.d(TAG, "CVM method = " + historyItem.getCvmMethod());
                Log.d(TAG, "------------------------------------------------------------");
            }
        }

    }

    public void bringToFront(Context context) {
        /* Bring this application back into the foreground */
        final Intent notificationIntent = new Intent(context, OrderSuccessfulActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notificationIntent);
        Log.i(TAG, "Bring OrderSuccessfulActivity to the front");
    }

    public String byteArrayToHexString(final byte[] byteArray) {
        if (byteArray == null) {
            return "";
            //throw new IllegalArgumentException("Argument 'byteArray' cannot be null");
        }
        int readBytes = byteArray.length;
        StringBuilder hexData = new StringBuilder();
        int onebyte;
        for (byte b : byteArray) {
            onebyte = (0x000000ff & b) | 0xffffff00;
            hexData.append(Integer.toHexString(onebyte).substring(6));
        }
        return hexData.toString().toUpperCase();
    }

    public static void Sleep(int iTimeoutMS) {
        try {
            Thread.sleep(iTimeoutMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private int start(IPrinter printer) {
        try {
            while (true) {
                int ret = printer.start();
                // printer is busy, please wait
                if (ret == 1) {
                    Sleep(1000);
                    continue;
                } else if (ret == 2) {
                    Log.i(TAG, "Printer is Out of Paper");
                    return -1;
                } else if (ret == 8) {
                    Log.i(TAG, "Printer is too hot");
                    return -1;
                } else if (ret == 9) {
                    Log.i(TAG, "Voltage is too low!");
                    return -1;
                } else if (ret != 0) {
                    return -1;
                }

                return 0;
            }
        } catch (PrinterDevException e) {
            e.printStackTrace();
            return 0;
        }
    }


    static public int psFailureUnknown = 99999; // A random high Value for now
    static public int psSuccess = 0;
    static public int psBusy = 1;

    public int getPrinterStatus(IPrinter iPaxPrinter) {
        try {
            int status = iPaxPrinter.getStatus();
            if (status == -4) { // Pax library error returned -4 (font not installed) when it shouldn't (raised a case with pax)
                status = psSuccess;
            }
            return status;
        } catch (Exception ex) {
        }
        return psFailureUnknown;
    }

    public int printBitmap(Context context, Bitmap printable) {
        int status = psSuccess;
        try {
            IDAL iPaxDal = NeptuneLiteUser.getInstance().getDal(context);
            IPrinter iPaxPrinter = iPaxDal.getPrinter();
            iPaxPrinter.init();
            iPaxPrinter.setGray(4);

            iPaxPrinter.printBitmap(printable);
            /*Spool Out the receipt*/
            iPaxPrinter.step(150);

            start(iPaxPrinter);
            /*Wait for the Printing to Occur*/

            do {
                status = this.getPrinterStatus(iPaxPrinter);

                if (status == psBusy) {
                    /*Printer is doing its job*/
                    Thread.sleep(200);
                } else {
                    break;
                }

            } while (status != psSuccess);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}
