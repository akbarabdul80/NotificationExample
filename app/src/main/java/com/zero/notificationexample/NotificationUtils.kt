package com.zero.notificationexample

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

@SuppressLint("UnspecifiedImmutableFlag")
object NotificationUtils {

    public fun sendNotification(context: Context) {
        val mNotificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(
            context,
            MainActivity.NOTIF_CHANNEL_ID
        )
        builder.setContentTitle("You've been notified")
        builder.setContentText("This is notification text")
        builder.setSmallIcon(R.drawable.ic_notification)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        val contentIntent = Intent(context, MainActivity::class.java)
        val pendingContentIntent = PendingIntent.getActivity(
            context,
            MainActivity.NOTIF_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingContentIntent)
        val learnMoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.RHCOMP_URL))
        val learnMorePendingIntent = PendingIntent.getActivity(
            context,
            MainActivity.NOTIF_ID,
            learnMoreIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.addAction(
            R.drawable.ic_notification,
            "Open Github",
            learnMorePendingIntent
        )
        val updateIntent = Intent(MainActivity.UPDATE_EVENT)
        val updatePendingIntent = PendingIntent.getBroadcast(
            context,
            MainActivity.NOTIF_ID,
            updateIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.addAction(R.drawable.ic_notification, "Update", updatePendingIntent)
        val notification: Notification = builder.build()
        mNotificationManager.notify(MainActivity.NOTIF_ID, notification)
    }

    fun updateNotification(context: Context) {
        val mNotificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(
            context,
            MainActivity.NOTIF_CHANNEL_ID
        )
        builder.setContentTitle("You've been notified")
        builder.setContentText("Open Github!")
        builder.setSmallIcon(R.drawable.ic_notification)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        val mascotBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_logo)
        builder.setStyle(
            NotificationCompat.BigPictureStyle().bigPicture(mascotBitmap)
                .setBigContentTitle("This notification has been updated")
        )
        val contentIntent = Intent(context, MainActivity::class.java)
        val pendingContentIntent = PendingIntent.getActivity(
            context,
            MainActivity.NOTIF_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingContentIntent)
        val notification: Notification = builder.build()
        mNotificationManager.notify(MainActivity.NOTIF_ID, notification)
    }

    fun cancelNotification(context: Context) {
        val mNotificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.cancel(MainActivity.NOTIF_ID)
    }

}
