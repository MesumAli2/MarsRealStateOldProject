package com.example.marsrealstate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class NotifiyWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val appContext = applicationContext

        val resourceUri = inputData.getString("ImagesReceived")
        makeStatusNotification("ImagesReceived $resourceUri", appContext)

        return Result.success()

    }


    fun makeStatusNotification(message: String, context: Context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = "Verbose WorkManager Notifications"
            val description = "Shows notifications whenever work starts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("VERBOSE_NOTIFICATION", name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, "VERBOSE_NOTIFICATION")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Explore Mars")
            .setContentText(message)

            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }
}