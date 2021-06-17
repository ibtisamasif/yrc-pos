package com.yrc.pos.core.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.yrc.pos.core.Constants

object User {

    private lateinit var userPreferences: SharedPreferences
    private const val userPreferenceName = "UserPreferences"

    fun initialize(context: Context){
        userPreferences = context.getSharedPreferences(userPreferenceName, Context.MODE_PRIVATE)
    }

    fun wipeUserData() {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.remove(UserConstants.Key_User_Profile)
        preferenceEditor.remove(UserConstants.Key_Profile_Status)
        preferenceEditor.apply()
    }
}