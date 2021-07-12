package com.yrc.pos.core

import com.yrc.pos.core.providers.models.Ticket
import java.util.*


object Constants {
    const val EMPTY_STRING = ""
    const val SPACE_STRING = " "
    const val LOGGER_TAG = "Yrc App"
}

object Fonts {
    const val titillium_web_BOLD = "fonts/titillium-web_bold.ttf"
    const val titillium_web_LIGHT = "fonts/titillium-web_light.ttf"
    const val titillium_web_REGULAR = "fonts/titillium-web_regular.ttf"
}

object EndPoints {
    const val API_LOGIN = "/api/auth/login"
    const val API_GET_TICKET_INFO = "/api/tickets/{enclosure}"
    const val API_POST_VALIDATE_OLD_VOUCHER = "/api/vouchers/validateOldVoucher"
    const val API_POST_VALIDATE_NEW_VOUCHER = "/api/vouchers/validateNewVoucher"
    const val API_POST_REGISTER_ORDER = "/api/orders/registerOrder"
    const val API_POST_COMPLETE_ORDER = "/api/orders/completeOrder"
}