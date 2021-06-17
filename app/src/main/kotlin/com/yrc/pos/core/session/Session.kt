package com.yrc.pos.core.session

import android.content.Context
import android.content.SharedPreferences
import com.yrc.pos.core.Constants

object Session {

    private lateinit var sessionPreferences: SharedPreferences
    private const val sessionPreferenceName = "SessionPreferences"

    fun initialize(context: Context){
        sessionPreferences = context.getSharedPreferences(sessionPreferenceName, Context.MODE_PRIVATE)
    }

    fun storeSession(accessToken: String?) {
        val preferenceEditor = sessionPreferences.edit()
        preferenceEditor.putString(SessionConstants.Key_AccessToken, accessToken)
        preferenceEditor.apply()
    }

    fun isSessionAvailable() : Boolean {
        return sessionPreferences.getString(SessionConstants.Key_AccessToken, Constants.EMPTY_STRING).isNotEmpty()
    }

    fun getAccessToken() : String {
        return sessionPreferences.getString(SessionConstants.Key_AccessToken, Constants.EMPTY_STRING)
    }

    fun clearSession() {
        val preferenceEditor = sessionPreferences.edit()
        preferenceEditor.remove(SessionConstants.Key_AccessToken)
        preferenceEditor.apply()
        User.wipeUserData()
    }
}