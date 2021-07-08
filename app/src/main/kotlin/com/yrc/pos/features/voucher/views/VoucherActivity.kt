package com.yrc.pos.features.voucher.views

import android.os.Bundle
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import kotlinx.android.synthetic.main.activity_voucher.*

class VoucherActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher)

        setListeners()
    }

    private fun setListeners() {
        buttonClearVouchers.setOnClickListener {
            textViewVouchersAppliedAmount.text = "£0.00"
        }
        buttonReturnToBasket.setOnClickListener {
            finish()
        }
    }

}
