/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.eggtimernotifications.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.android.eggtimernotifications.MainActivity
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.receiver.SnoozeReceiver

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val pending= PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        // Specifies option to use another one or use the current one
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    // TODO: Step 2.0 add style
    // Load image
    val eggimage= BitmapFactory.decodeResource(
        applicationContext.resources ,
        R.drawable.cooked_egg
    )
    // BigPicture
    val newstyle= NotificationCompat.BigPictureStyle()
        .bigPicture(eggimage)
        // Large icon goes away when it is expanded
        .bigLargeIcon(null)

    // TODO: Step 2.2 add snooze action
    val snoozeIntent= Intent(applicationContext, SnoozeReceiver::class.java)
    val snoozePendingintent = PendingIntent.getBroadcast(
        applicationContext,
        NOTIFICATION_ID,
        snoozeIntent,
        FLAGS
    )

    // TODO: Step 1.2 get an instance of NotificationCompat.Builder
    val builder= NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.egg_notification_channel_id)
    )

    // TODO: Step 1.8 use the new 'breakfast' notification channel

        .setSmallIcon(R.drawable.egg_icon)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)

        .setContentIntent(pending)
        // when user taps on notification, it will dismiss itself as it redirects to app
        .setAutoCancel(true)
        .setStyle(newstyle)
        .setLargeIcon(eggimage)
        .addAction(
            R.drawable.egg_icon,
            applicationContext.getString(R.string.snooze),
            snoozePendingintent
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // TODO: Step 2.5 set priority

    notify(NOTIFICATION_ID,builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
