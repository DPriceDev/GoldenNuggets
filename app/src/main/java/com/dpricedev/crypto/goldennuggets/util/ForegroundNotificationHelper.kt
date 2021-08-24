package com.dpricedev.crypto.goldennuggets.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dpricedev.crypto.goldennuggets.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ForegroundNotificationHelper {
    fun getNotification(notificationIntent: PendingIntent) : Notification
}

class ForegroundNotificationHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) : ForegroundNotificationHelper {

    override fun getNotification(notificationIntent: PendingIntent): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setSound(null)
            .setContentIntent(notificationIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createChannel())
        }

        return builder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() =
        // 1
        NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {

            // 2
            description = CHANNEL_DESCRIPTION
            setSound(null, null)
        }

    companion object {
        private const val CHANNEL_ID = "sfsdf"
        private const val CHANNEL_NAME = "Golden Nugget BlockChain"
        private const val CHANNEL_DESCRIPTION = "desc"
    }
}