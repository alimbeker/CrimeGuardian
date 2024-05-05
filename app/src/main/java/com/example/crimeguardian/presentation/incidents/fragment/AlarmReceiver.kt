package com.example.crimeguardian.presentation.incidents.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("Notification") ?: "Default Message" // Corrected extra key
        Log.d("ToDo", "Alarm message: $message")

        // Create and show a notification using NotificationHelper
        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotification("Be careful!", message) // Swap title and message
    }
}