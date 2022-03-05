package com.zero.notificationexample

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.zero.notificationexample.databinding.ActivityMainBinding

@SuppressLint("UnspecifiedImmutableFlag")
class MainActivity : AppCompatActivity() {

    companion object {
        const val RHCOMP_URL = "https://github.com/akbarabdul80"
        const val UPDATE_EVENT = "update-notif-event"
        const val NOTIF_CHANNEL_ID: String = BuildConfig.APPLICATION_ID
        const val NOTIF_ID = 0
    }

    private var mNotificationManager: NotificationManager? = null
    private var mNotifReceiver: NotificationReceiver? = null
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mNotifReceiver = NotificationReceiver()
        registerReceiver(mNotifReceiver, IntentFilter(UPDATE_EVENT))
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIF_CHANNEL_ID, "zero_notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager!!.createNotificationChannel(channel)
        }

        with(binding) {
            btnNotify.onClick { sendNotification() }
            btnUpdate.onClick { updateNotification() }
            btnCancel.onClick { cancelNotification() }
        }

    }

    private fun sendNotification() {
        NotificationUtils.sendNotification(baseContext)
        with(binding) {
            btnNotify.isEnabled = false
            btnUpdate.isEnabled = true
            btnCancel.isEnabled = true
        }

    }

    private fun updateNotification() {
        NotificationUtils.updateNotification(baseContext)
        with(binding) {
            btnNotify.isEnabled = false
            btnUpdate.isEnabled = false
            btnCancel.isEnabled = true
        }
    }

    private fun cancelNotification() {
        NotificationUtils.cancelNotification(baseContext)
        with(binding) {
            btnNotify.isEnabled = true
            btnUpdate.isEnabled = false
            btnCancel.isEnabled = false
        }
    }

    inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action === UPDATE_EVENT) updateNotification()
        }
    }
}