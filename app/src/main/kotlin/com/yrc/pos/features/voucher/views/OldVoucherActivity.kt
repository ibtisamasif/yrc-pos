package com.yrc.pos.features.voucher.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.TicketVM
import com.yrc.pos.core.TicketVM.deviceSerial
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.voucher.viewmodels.NewVouchersVM
import com.yrc.pos.features.voucher.viewmodels.OldVoucherVM
import com.yrc.pos.features.voucher.voucher_service.ValidateOldVoucherRequest
import com.yrc.pos.features.voucher.voucher_service.ValidateOldVoucherResponse
import kotlinx.android.synthetic.main.activity_old_voucher.*
import java.text.DecimalFormat

class OldVoucherActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_voucher)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun setListeners() {

        buttonApplyOldVoucher.setOnClickListener {

            val orderTotal = TicketVM.getSubtotal()

            if (checkValidations()) {
                APiManager.postValidateOldVoucher(
                    this,
                    this,
                    ValidateOldVoucherRequest(
                        deviceSerial,
                        editTextVoucherField.text.toString(),
                        orderTotal.toString()
                    )
                )
            }

        }

        buttonClearVouchers.setOnClickListener {
            OldVoucherVM.clear()
            updateUI()
        }

        buttonReturnToBasket.setOnClickListener {
            finish()
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)
        when (apiResponse) {

            is ValidateOldVoucherResponse -> {

                OldVoucherVM.oldVoucherRedeemedTotal =
                    editTextVoucherField.text.toString().toDouble()

                updateUI(true)
            }
        }
    }

    private fun updateUI(resetFields: Boolean = false) {
        textViewSubtotalAmount.text = "£${DecimalFormat("##.##").format(TicketVM.getSubtotal())}"
        textViewVouchersAppliedAmount.text =
            "£${DecimalFormat("##.##").format(OldVoucherVM.oldVoucherRedeemedTotal + NewVouchersVM.newVouchersRedeemedTotal)}"
        textViewTotalAmount.text =
            "£${DecimalFormat("##.##").format(TicketVM.getSubtotal() - (OldVoucherVM.oldVoucherRedeemedTotal + NewVouchersVM.newVouchersRedeemedTotal))}"

        if (resetFields)
            editTextVoucherField.setText("")
    }

    private fun checkValidations(): Boolean {

        if (editTextVoucherField.text.isEmpty()) {
            editTextVoucherField.error = "Please enter gift voucher"
            return false
        }

        return true
    }

}
