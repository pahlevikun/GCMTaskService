package com.deggan.gcmtaskscheduler

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log


/**
 * Created by farhan on 5/11/18.
 */
class GCMTaskNotif {
    companion object {
        private val TAG = "HASIL ${GCMTaskService::class.java.simpleName}"

        fun isNotificationVisible(context: Context): Boolean {
            val notificationIntent = Intent(context, MainActivity::class.java)
            val test = PendingIntent.getActivity(context, 0, notificationIntent,
                    PendingIntent.FLAG_NO_CREATE)
            return test != null
        }

        fun hideNotification(context: Context){
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.cancel(0)
        }

        fun showNotification(context: Context, title: String?, body: String?, summaryBig: String?,
                             bigText: String?) {
            Log.d(TAG, "Create Notification")

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder = NotificationCompat.Builder(context, "NOTIFICATION")
            builder.setStyle(
                    NotificationCompat.BigTextStyle(builder)
                            .bigText(bigText)
                            .setBigContentTitle(title)
                            .setSummaryText(summaryBig))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.FLAG_NO_CLEAR)
                    .setContentIntent(pendingIntent)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.notify(0, builder.build())
        }
    }
}