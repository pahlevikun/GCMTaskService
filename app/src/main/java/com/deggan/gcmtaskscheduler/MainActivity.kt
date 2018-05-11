package com.deggan.gcmtaskscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.jetbrains.anko.appcompat.v7.switchCompat
import org.jetbrains.anko.relativeLayout

class MainActivity : AppCompatActivity() {

    private val TAG = "HASIL ${GCMTaskService::class.java.simpleName}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val autoConnectManager = AutoConnectManager(this)

        relativeLayout {
            //tools:context = com.deggan.gcmtaskscheduler.MainActivity //not support attribute
            switchCompat {
                text = "Background Service"
                isChecked = autoConnectManager.isAutoConnect
                setOnCheckedChangeListener { compoundButton, b ->
                    if (compoundButton.isChecked) {
                        Log.d(TAG, "TRUE")
                        autoConnectManager.startAutoConnect()
                        GCMTaskService.cancelRepeat(applicationContext)
                        GCMTaskService.scheduleRepeat(applicationContext)
                        if (!GCMTaskNotif.isNotificationVisible(this@MainActivity)) {
                            GCMTaskNotif.showNotification(this@MainActivity, "Wifi.id AutoConnect",
                                    "Starting AutoConnect Service", "@wifi.id",
                                    "Scanning New Access-Point")
                        }
                    } else {
                        Log.d(TAG, "FALSE")
                        autoConnectManager.endAutoConnect()
                        GCMTaskService.cancelAll(applicationContext)
                        GCMTaskService.cancelRepeat(applicationContext)
                        GCMTaskNotif.hideNotification(this@MainActivity)
                    }
                }
            }
        }
    }
}
