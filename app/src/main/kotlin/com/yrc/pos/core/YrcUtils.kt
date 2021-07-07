package com.yrc.pos.core

import android.content.Context
import android.net.ConnectivityManager
import android.util.Patterns
import java.util.*

object YrcUtils {

    fun isNetworkAvailable (context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo != null && networkInfo.isConnected
    }

}