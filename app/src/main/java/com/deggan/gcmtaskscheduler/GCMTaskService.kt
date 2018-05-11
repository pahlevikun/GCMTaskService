/*
 * ServiceSendData.kt Created with ♥ by PT. Deggan Technowave on 4/17/18 11:43 AM
 * PT. Deggan Technowave Copyright (c) 2018. All Rights Reserved.
 * Last Modified 4/17/18 11:42 AM
 */

package com.deggan.gcmtaskscheduler

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.gms.gcm.*

/**
 * Created by farhan on 4/17/18.
 */

class GCMTaskService : GcmTaskService() {

    override fun onRunTask(taskParams: TaskParams): Int {
        //do some stuff (mostly network) - executed in background thread (async)
        //obtain your data
//        val extras = taskParams.extras

        val h = Handler(mainLooper)
        Log.v(TAG, "onRunTask")
        if (taskParams.tag == GCM_ONEOFF_TAG) {
            h.post {
                Log.d(TAG, "ONLY ONE")
            }
        } else if (taskParams.tag == GCM_REPEAT_TAG) {
            h.post {
                Log.d(TAG, "REPEAT")
                checkSsid()
            }
        }
        return GcmNetworkManager.RESULT_SUCCESS
    }

    companion object {

        private val TAG = "HASIL ${GCMTaskService::class.java.simpleName}"

        const val GCM_ONEOFF_TAG = "oneoff|[0,0]"
        const val GCM_REPEAT_TAG = "repeat|[7200,1800]"

        fun scheduleOneOff(context: Context) {
            //in this method, single OneOff task is scheduled (the target service that will be called is MyTaskService.class)
            val data = Bundle()
            data.putString("some key", "some budle data")
            try {
                val oneoff = OneoffTask.Builder()
                        //specify target service - must extend GcmTaskService
                        .setService(GCMTaskService::class.java)
                        //tag that is unique to this task (can be used to cancel task)
                        .setTag(GCM_ONEOFF_TAG)
                        //executed between 0 - 10s from now
                        .setExecutionWindow(10, 10)
                        //set required network state, this line is optional
                        .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                        //request that charging must be connected, this line is optional
                        .setRequiresCharging(false)
                        //set some data we want to pass to our task
                        .setExtras(data)
                        //if another task with same tag is already scheduled, replace it with this task
                        .setUpdateCurrent(true)
                        .build()
                GcmNetworkManager.getInstance(context).schedule(oneoff)
                Log.v(TAG, "oneoff task scheduled")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun scheduleRepeat(context: Context) {
            //in this method, single Repeating task is scheduled (the target service that will be called is MyTaskService.class)
            try {
                val periodic = PeriodicTask.Builder()
                        //specify target service - must extend GcmTaskService
                        .setService(GCMTaskService::class.java)
                        //repeat every 60 seconds
                        .setPeriod(20)
                        //specify how much earlier the task can be executed (in seconds)
                        .setFlex(20)
                        //tag that is unique to this task (can be used to cancel task)
                        .setTag(GCM_REPEAT_TAG)
                        //whether the task persists after device reboot
                        .setPersisted(true)
                        //if another task with same tag is already scheduled, replace it with this task
                        .setUpdateCurrent(true)
                        //set required network state, this line is optional
                        .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                        //request that charging must be connected, this line is optional
                        .setRequiresCharging(false)
                        .build()
                GcmNetworkManager.getInstance(context).schedule(periodic)
                Log.v(TAG, "repeating task scheduled")
            } catch (e: Exception) {
                Log.e(TAG, "scheduling failed")
                e.printStackTrace()
            }

        }

        fun cancelOneOff(context: Context) {
            GcmNetworkManager
                    .getInstance(context)
                    .cancelTask(GCM_ONEOFF_TAG, GCMTaskService::class.java)
        }

        fun cancelRepeat(context: Context) {
            GcmNetworkManager
                    .getInstance(context)
                    .cancelTask(GCM_REPEAT_TAG, GCMTaskService::class.java)
        }

        fun cancelAll(context: Context) {
            GcmNetworkManager
                    .getInstance(context)
                    .cancelAllTasks(GCMTaskService::class.java)
        }
    }

    private fun checkSsid() {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val autoConnectManager = AutoConnectManager(applicationContext)
        if (connectivityManager.activeNetworkInfo != null &&
                connectivityManager.activeNetworkInfo.isConnected) {
            if (autoConnectManager.isAutoConnect){
                Log.d(TAG,"SCANNING AUTO CONNECT")
            }
        }
    }

}
