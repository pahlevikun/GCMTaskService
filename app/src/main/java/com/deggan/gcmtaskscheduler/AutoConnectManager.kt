/*
 * SessionManager Created with ♥ by PT. Deggan Technowave on 1/8/18 11:36 AM
 * PT. Deggan Technowave Copyright (c) 2018. All Rights Reserved.
 * Last Modified 1/8/18 10:59 AM
 */

package com.deggan.gcmtaskscheduler

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by farhan on 1/8/18.
 */

class AutoConnectManager
(private val context: Context) {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0

    val isAutoConnect: Boolean
        get() = pref.getBoolean(context.getString(R.string.sharedPrefItemAutoConnect), false)

    init {
        pref = context.getSharedPreferences(context.getString(R.string.sharePrefData),
                PRIVATE_MODE)
        editor = pref.edit()
    }

    fun startAutoConnect() {
        editor.putBoolean(context.getString(R.string.sharedPrefItemAutoConnect), true)
        editor.commit()
    }

    fun endAutoConnect() {
        editor.clear()
        editor.commit()
    }
}