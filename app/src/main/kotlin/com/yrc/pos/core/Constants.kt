package com.yrc.pos.core

import com.yrc.pos.core.providers.Ticket

object Constants {
    const val EMPTY_STRING = ""
    const val SPACE_STRING = " "
    const val LOGGER_TAG = "Yrc App"
    const val NUMBER_REGULAR_EXPRESSION = "^[\\+\\d]?(?:[\\d-.\\s()]*)\$"
}

object Fonts {
    const val titillium_web_BOLD = "fonts/titillium-web_bold.ttf"
    const val titillium_web_LIGHT = "fonts/titillium-web_light.ttf"
    const val titillium_web_REGULAR = "fonts/titillium-web_regular.ttf"
}

object EndPoints {
    const val API_LOGIN = "/api/auth/login"
    const val API_GET_TICKET_INFO = "/api/tickets/{enclosure}"
}

object Prices {
    var tickets = listOf<Ticket>()
}