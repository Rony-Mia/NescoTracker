package com.example.nescotracker

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context

/**
 * হোম স্ক্রিন উইজেট: অ্যাপ না খুলেই সবচেয়ে জরুরি মিটারের ব্যালেন্স দেখা যায়।
 * প্রকৃত ডেটা লোডিং WidgetUpdater-এ হয়, যেটা অ্যাপের যেকোনো ব্যালেন্স-রিফ্রেশের পরও কল হয়।
 */
class BalanceWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        WidgetUpdater.refreshAll(context)
    }

    override fun onEnabled(context: Context) {
        WidgetUpdater.refreshAll(context)
    }
}
