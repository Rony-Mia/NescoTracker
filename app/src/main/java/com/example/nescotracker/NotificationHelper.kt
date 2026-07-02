package com.example.nescotracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.nescotracker.data.Account

object NotificationHelper {

    private const val CHANNEL_LOW_BALANCE = "nesco_low_balance_channel"
    private const val CHANNEL_REMINDER = "nesco_reminder_channel"

    private fun ensureChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_LOW_BALANCE,
                    "Low Balance Alert",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply { description = "ব্যালেন্স কমে গেলে সতর্কতা" }
            )

            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_REMINDER,
                    "Recharge Reminder",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply { description = "নিয়মিত রিচার্জ মনে করিয়ে দেওয়ার নোটিফিকেশন" }
            )
        }
    }

    private fun openAppPendingIntent(context: Context, accountId: Long): PendingIntent {
        val intent = Intent(context, AccountDetailActivity::class.java).apply {
            putExtra(AccountDetailActivity.EXTRA_ACCOUNT_ID, accountId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context, accountId.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun showLowBalanceAlert(context: Context, account: Account, balance: Float, daysLeft: Int?) {
        ensureChannels(context)
        val message = if (daysLeft != null && daysLeft > 0) {
            "${account.nickname}: বর্তমান ব্যালেন্স ৳${"%.2f".format(balance)}। আনুমানিক আর $daysLeft দিন চলবে। এখনই রিচার্জ করুন।"
        } else {
            "${account.nickname}: বর্তমান ব্যালেন্স ৳${"%.2f".format(balance)}। দ্রুত রিচার্জ করুন।"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_LOW_BALANCE)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("⚠️ ব্যালেন্স কম!")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(openAppPendingIntent(context, account.id))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1000 + account.id.toInt(), notification)
    }

    fun showRechargeReminder(context: Context, account: Account, balance: Float?) {
        ensureChannels(context)
        val message = if (balance != null) {
            "${account.nickname} মিটারের ব্যালেন্স চেক করার সময় হয়েছে। বর্তমান ব্যালেন্স: ৳${"%.2f".format(balance)}"
        } else {
            "${account.nickname} মিটারের ব্যালেন্স চেক করার সময় হয়েছে।"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_REMINDER)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🔔 রিচার্জ রিমাইন্ডার")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(openAppPendingIntent(context, account.id))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(2000 + account.id.toInt(), notification)
    }
}
