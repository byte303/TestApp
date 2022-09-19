package frog.company.testapplication.helper

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import frog.company.testapplication.R
import frog.company.testapplication.MainActivity

object UtilsNotifications {

    fun showNotificationBar(context: Context) {
        val channelID = "notify_001"
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelID, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = channelID
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Уведомление")
            .setContentText("Тестовое уведомление!")
            .setContentIntent(pendingIntent)
        notificationManager.notify(2, notificationBuilder.build())
    }

    class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null) {
                showNotificationBar(context)
            }
        }
    }
}