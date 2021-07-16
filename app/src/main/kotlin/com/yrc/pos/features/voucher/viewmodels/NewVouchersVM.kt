package com.yrc.pos.features.voucher.viewmodels

import com.yrc.pos.features.payment.payment_service.NewVouchersRedeemed

object NewVouchersVM {
    var newVouchersRedeemedTotal = 0.0
    var newVouchersRedeemed: ArrayList<NewVouchersRedeemed> = arrayListOf()

    fun clear() {
        newVouchersRedeemedTotal = 0.0
        newVouchersRedeemed = arrayListOf()
    }
}
