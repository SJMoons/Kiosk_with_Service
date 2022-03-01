package com.example.kiosk_ui_event

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.session.PlaybackState.ACTION_STOP
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {
    lateinit var notificationManager: NotificationManager
    lateinit var builder : NotificationCompat.Builder
    companion object {
        const val NOTIFICATION_ID = 10
        const val CHANNEL_ID = "primary_notification_channel"
    }
    override fun onCreate() {
        super.onCreate()
        builder = NotificationCompat.Builder(this, "channel")
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MyService is running")
                .setContentText("MyService is running")
                .build()
            Log.d("Test", "start foreground")
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var menuCount = intent?.getStringExtra("menuCount")
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "MyApp notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "AppApp Tests"

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            notificationChannel)
    }



    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}