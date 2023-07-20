package com.example.smartex

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.smartex.ui.MainActivity

class MyServiceOne : Service() {

    private val CHANNEL_ID = "ForegroundService Kotlin"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer expired")
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true).clearPeople()
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        with(NotificationManagerCompat.from(this)) {
            if (areNotificationsEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this@MyServiceOne,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val notification = builder.build()
                    startForeground(3, notification)
                    notify(3, notification)
                }
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, MyServiceOne::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

//        fun stopService(context: Context) {
//            val stopIntent = Intent(context, MyServiceOne::class.java)
//            context.stopService(stopIntent)
//        }
    }
}