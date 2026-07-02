package com.example.nescotracker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nescotracker.data.Repository

/**
 * প্রতিটি সক্রিয় অ্যাকাউন্টের ব্যালেন্স ব্যাকগ্রাউন্ডে চেক করে।
 * - ব্যালেন্স লিমিটের নিচে নামলে low-balance notification পাঠায়
 * - reminderIntervalDays পার হয়ে গেলে recharge reminder পাঠায়
 */
class BalanceWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val repository = Repository(applicationContext)

        return try {
            val accounts = repository.getActiveAccounts()
            if (accounts.isEmpty()) return Result.success()

            for (account in accounts) {
                val balance = repository.checkAndSaveBalance(account) ?: continue

                // ১. Low balance check
                if (balance <= account.lowBalanceLimit) {
                    val estimate = repository.calculateUsageEstimate(account.id, balance)
                    NotificationHelper.showLowBalanceAlert(
                        applicationContext, account, balance, estimate.estimatedDaysLeft
                    )
                }

                // ২. Recharge reminder (নির্দিষ্ট দিন পরপর, ব্যালেন্স যাই থাকুক)
                val daysSinceReminder =
                    (System.currentTimeMillis() - account.lastReminderSentAt) / (1000L * 60 * 60 * 24)
                if (account.reminderIntervalDays > 0 && daysSinceReminder >= account.reminderIntervalDays) {
                    NotificationHelper.showRechargeReminder(applicationContext, account, balance)
                    repository.markReminderSent(account.id)
                }
            }

            WidgetUpdater.refreshAll(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "NescoBalanceCheckWork"
    }
}
