package com.yrc.pos.features.voucher.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.TicketVM.deviceSerial
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.payment.payment_service.NewVouchersRedeemed
import com.yrc.pos.features.voucher.viewmodels.NewVouchersVM
import com.yrc.pos.features.voucher.voucher_service.ValidateNewVoucherRequest
import com.yrc.pos.features.voucher.voucher_service.ValidateNewVoucherResponse
import kotlinx.android.synthetic.main.activity_new_voucher.*

class NewVoucherActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_voucher)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun setListeners() {

        buttonApplyNewVoucher.setOnClickListener {

            val orderTotal = TicketVM.getSubtotal()

            APiManager.postValidateNewVoucher(
                this,
                this,
                ValidateNewVoucherRequest(
                    deviceSerial,
                    editTextVoucherCodeField.text.toString(),
                    editTextVoucherPinField.text.toString(),
                    orderTotal.toString()
                )
            )

        }

        buttonClearVouchers.setOnClickListener {
            NewVouchersVM.clear()
            updateUI()
        }

        buttonReturnToBasket.setOnClickListener {
            finish()
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        when (apiResponse) {

            is ValidateNewVoucherResponse -> {

                NewVouchersVM.newVouchersRedeemedTotal =
                    NewVouchersVM.newVouchersRedeemedTotal + apiResponse.voucherAmount

                NewVouchersVM.newVouchersRedeemed.add(
                    NewVouchersRedeemed(
                        editTextVoucherCodeField.text.toString(),
                        apiResponse.voucherAmount.toString()
                    )
                )

                updateUI()
            }
        }
    }

    private fun updateUI() {
        textViewSubtotalAmount.text = "£${TicketVM.getSubtotal()}"
        textViewVouchersAppliedAmount.text = "£${NewVouchersVM.newVouchersRedeemedTotal}"
        textViewTotalAmount.text =
            "£${TicketVM.getSubtotal() - NewVouchersVM.newVouchersRedeemedTotal}"
    }

}
