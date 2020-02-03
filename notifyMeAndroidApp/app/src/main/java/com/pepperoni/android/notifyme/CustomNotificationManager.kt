package com.pepperoni.android.notifyme

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pepperoni.android.notifyme.NotificationBuildingData.NOTIFICATION_CHANNEL_ID
import com.pepperoni.android.notifyme.NotificationBuildingData.NOTIFICATION_TAG
import com.pepperoni.android.notifyme.NotificationBuildingData.createNotificationChannels
import com.pepperoni.android.notifyme.mainActivity.MainActivity

class CustomNotificationManager private constructor(){

    companion object{
        val instance = CustomNotificationManager()
    }

    //TODO: notification groups

    fun showNotification(
        context: Context,
        icon: String,
        title: String,
        content: String){

        createNotificationChannels(context, "mainNotifyChannel")
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val contentIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val deleteIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            //.setSmallIcon(context.get)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(contentIntent)
            .setDeleteIntent(deleteIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_TAG, 11111111, builder.build())
        }
    }
}