package com.yrc.pos.core

object Constants {
    const val EMPTY_STRING = ""
    const val SPACE_STRING = " "
    const val LOGGER_TAG = "Yrc App"
    const val DOB_FORMAT = "MMM/dd/yyyy"
    const val NUMBER_REGULAR_EXPRESSION = "^[\\+\\d]?(?:[\\d-.\\s()]*)\$"
}

object Fonts {
    const val titillium_web_BOLD = "fonts/titillium-web_bold.ttf"
    const val titillium_web_LIGHT = "fonts/titillium-web_light.ttf"
    const val titillium_web_REGULAR = "fonts/titillium-web_regular.ttf"
}

object EndPoints {
    const val API_LOGIN = "/api/auth/login"
    const val API_GRANDSTAND = "/api/tickets/grandstand?device_id=123456789"
    const val API_CLOCKTOWER = "/api/tickets/clocktower?device_id=123456789"
}

object Prices {
    var PRICE_ADULT = 20
    const val PRICE_OVER65 = 30
    const val PRICE_1822 = 40
    const val PRICE_RACEGOER = 50
}