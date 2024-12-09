package com.example.hangoutz.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.hangoutz.R
import java.util.UUID

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val eventName = inputData.getString("eventName") ?: return Result.failure()
        val eventId = inputData.getString("eventId") ?: return Result.failure()

        // Proveravamo da li aplikacija ima dozvolu za prikazivanje notifikacija (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(applicationContext).areNotificationsEnabled()) {
                Log.e("NotificationWorker", "Notifications are disabled for this app.")
                // Ako notifikacije nisu omogućene, pokreni aktivnost da korisnik omogući notifikacije
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, applicationContext.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
                return Result.failure() // Ne možemo poslati notifikaciju dok korisnik ne omogući
            }
        }

        // Priprema NotificationManager za slanje notifikacije
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Kreiranje kanala za notifikacije (Android 8.0 i noviji)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_notifications",
                "Event Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Kreiranje notifikacije
        val notification = NotificationCompat.Builder(applicationContext, "event_notifications")
            .setSmallIcon(R.drawable.avatar_default)
            .setContentTitle("Upcoming Event")
            .setContentText("Your event '$eventName' is starting soon!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Prikazivanje notifikacije sa jedinstvenim ID-em baziranim na eventId
        val notificationId = eventId.hashCode() // Možeš koristiti UUID ako želiš jedinstveniji ID
        notificationManager.notify(notificationId, notification)

        Log.d("NotificationWorker", "Notification sent for event: $eventName")

        return Result.success()
    }
}
