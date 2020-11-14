package com.example.exlibris.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat

object BookNotif {

    const val NEW_BOOK_ID = "1"
    private const val NEW_BOOK_NAME = "Nuevo Libro"
    private const val NEW_BOOK_DESCRIPTION =
            "Se han agregado nuevos libros a la lista"

    fun createNotificationForNewBook(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = NotificationManagerCompat.from(context)

            val channel = NotificationChannel(
                    NEW_BOOK_ID,
                    NEW_BOOK_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = NEW_BOOK_DESCRIPTION

            notificationManager.createNotificationChannel(channel)
        }
    }
}