package com.example.suci_loyalty.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.suci_loyalty.pertemuan10.PermohonanSuratActivity

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Pengingat Desa"
        val message = intent.getStringExtra("message") ?: "Waktunya mengecek status agenda Anda."
        val targetClassName = intent.getStringExtra("target_activity")

        val targetIntent = if (!targetClassName.isNullOrEmpty()) {
            val clazz = Class.forName(targetClassName)
            Intent(context, clazz).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        } else {
            Intent(context, PermohonanSuratActivity::class.java)
        }

        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = targetIntent
        )
    }
}