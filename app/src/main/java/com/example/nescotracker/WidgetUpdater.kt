package com.example.nescotracker

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.nescotracker.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * হোম স্ক্রিন উইজেট আপডেট করার কেন্দ্রীয় জায়গা।
 * সবচেয়ে "জরুরি" মিটার (সবচেয়ে কম ব্যালেন্স, অথবা প্রথম মিটার) দেখানো হয়,
 * সাথে বাকি কয়টা মিটার আছে সেটাও দেখানো হয়।
 */
object WidgetUpdater {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /** ব্যালেন্স চেক/রিফ্রেশ হওয়ার পর যেকোনো জায়গা থেকে কল করা যায় */
    fun refreshAll(context: Context) {
        val appContext = context.applicationContext
        val manager = AppWidgetManager.getInstance(appContext)
        val ids = manager.getAppWidgetIds(
            android.content.ComponentName(appContext, BalanceWidgetProvider::class.java)
        )
        if (ids.isEmpty()) return

        scope.launch {
            val repository = Repository(appContext)
            val accounts = repository.getActiveAccounts()

            val views = RemoteViews(appContext.packageName, R.layout.widget_balance)

            if (accounts.isEmpty()) {
                views.setTextViewText(R.id.widgetNickname, "⚡ NESCO Tracker")
                views.setTextViewText(R.id.widgetBalance, "৳---")
                views.setTextViewText(R.id.widgetStatus, "কোনো মিটার যোগ করা নেই")
                views.setTextViewText(R.id.widgetMoreCount, "")
            } else {
                // সবচেয়ে জরুরি (কম ব্যালেন্স) মিটার সামনে দেখানো হয়
                var primaryAccount = accounts.first()
                var primaryBalance = repository.getLatestRecord(primaryAccount.id)?.balance
                var primaryCheckedAt = repository.getLatestRecord(primaryAccount.id)?.checkedAt

                for (account in accounts) {
                    val record = repository.getLatestRecord(account.id)
                    if (record != null && (primaryBalance == null || record.balance < (primaryBalance ?: Float.MAX_VALUE))) {
                        primaryAccount = account
                        primaryBalance = record.balance
                        primaryCheckedAt = record.checkedAt
                    }
                }

                views.setTextViewText(R.id.widgetNickname, "⚡ ${primaryAccount.nickname}")

                if (primaryBalance != null) {
                    views.setTextViewText(R.id.widgetBalance, "৳${"%.0f".format(primaryBalance)}")
                    val isLow = primaryBalance!! <= primaryAccount.lowBalanceLimit
                    views.setTextColor(
                        R.id.widgetBalance,
                        android.graphics.Color.parseColor(if (isLow) "#E5484D" else "#12181B")
                    )
                    views.setTextViewText(
                        R.id.widgetStatus,
                        if (isLow) "⚠️ ব্যালেন্স কম, রিচার্জ করুন" else "✅ ব্যালেন্স ঠিক আছে"
                    )
                } else {
                    views.setTextViewText(R.id.widgetBalance, "৳---")
                    views.setTextViewText(R.id.widgetStatus, "এখনো চেক করা হয়নি")
                }

                val extraCount = accounts.size - 1
                views.setTextViewText(
                    R.id.widgetMoreCount,
                    if (extraCount > 0) "+$extraCount আরও" else ""
                )
            }

            val openAppIntent = Intent(appContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                appContext, 0, openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent)

            for (id in ids) {
                manager.updateAppWidget(id, views)
            }
        }
    }
}
