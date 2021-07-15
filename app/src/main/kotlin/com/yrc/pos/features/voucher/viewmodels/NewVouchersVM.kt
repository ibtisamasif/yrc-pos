package com.yrc.pos.features.voucher.viewmodels

import com.yrc.pos.features.payment.payment_service.NewVouchersRedeemed

object NewVouchersVM {
    val newVouchersRedeemedTotal: Double = 15.00
    val newVouchersRedeemed: List<NewVouchersRedeemed> = arrayListOf(NewVouchersRedeemed("123456789", "5.00"), NewVouchersRedeemed("123456789", "5.00"), NewVouchersRedeemed("123456789", "5.00"))
}
