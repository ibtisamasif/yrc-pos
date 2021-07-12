package com.yrc.pos.features.voucher.views

import android.os.Build
import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.voucher.voucher_service.ValidateOldVoucherRequest
import com.yrc.pos.features.voucher.voucher_service.ValidateOldVoucherResponse
import kotlinx.android.synthetic.main.activity_old_voucher.*

class OldVoucherActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_voucher)
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

        buttonApplyOldVoucher.setOnClickListener {
            val orderTotal = TicketVM.getSubtotal()
            APiManager.postValidateOldVoucher(this, this, ValidateOldVoucherRequest(Build.SERIAL, editTextVoucherField.text.toString(), orderTotal.toString()))
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
            is ValidateOldVoucherResponse -> {
                textViewVouchersAppliedAmount.text = "£${editTextVoucherField.text.toString().toDouble()}"
                textViewTotalAmount.text = "£${apiResponse.newOrderTotal?.toDouble()}"
            }
        }
    }

}
