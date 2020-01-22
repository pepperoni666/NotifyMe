package com.pepperoni.android.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

object NotificationBuildingData {

    const val NOTIFICATION_CHANNEL_ID = "notificationchannelid1"

    const val NOTIFICATION_TAG = "notifymenotificationtag"

    //TODO: notification groups

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteNotificationChannel(context: Context, channelId: String){
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.deleteNotificationChannel(channelId)
    }

    fun createNotificationChannels(context: Context, name: String, description: String? = null, importance: Int? = null){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if(!notificationManager.notificationChannels.any { it.id == NOTIFICATION_CHANNEL_ID }){
                val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance ?: NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description?.let{
                        this.description = it
                    }
                }

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

}