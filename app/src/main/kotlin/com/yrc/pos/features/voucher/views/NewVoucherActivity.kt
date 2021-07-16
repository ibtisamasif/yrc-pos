package com.yrc.pos.features.voucher.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.TicketVM.deviceSerial
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.voucher.voucher_service.ValidateNewVoucherRequest
import com.yrc.pos.features.voucher.voucher_service.ValidateNewVoucherResponse
import kotlinx.android.synthetic.main.activity_new_voucher.*

class NewVoucherActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_voucher)
        setUp()
        initViews()
        setListeners()
    }

    private fun setUp() {
    }

    private fun initViews() {
        textViewSubtotalAmount.text = "£${TicketVM.getSubtotal()}"
        textViewTotalAmount.text = "£${TicketVM.getSubtotal()}"
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
            textViewVouchersAppliedAmount.text = "£0.0"
            textViewTotalAmount.text = "£${TicketVM.getSubtotal()}"
        }

        buttonReturnToBasket.setOnClickListener {
            finish()
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)
        when (apiResponse) {
            is ValidateNewVoucherResponse -> {
                textViewVouchersAppliedAmount.text = "£${apiResponse.voucherAmount.toDouble()}"
                textViewTotalAmount.text = "£${apiResponse.newOrderTotal.toDouble()}"
            }
        }
    }

}
