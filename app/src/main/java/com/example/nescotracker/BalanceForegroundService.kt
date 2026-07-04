package com.example.nescotracker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.nescotracker.data.Repository
import kotlinx.coroutines.*

class BalanceForegroundService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(999, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startChecking()
        return START_STICKY // সিস্টেম সার্ভিসটি বন্ধ করলে আবার রিস্টার্ট করবে
    }

    private fun startChecking() {
        job?.cancel()
        job = serviceScope.launch {
            val repository = Repository(applicationContext)
            while (isActive) {
                try {
                    val accounts = repository.getActiveAccounts()
                    for (account in accounts) {
                        val balance = repository.checkAndSaveBalance(account)
                        if (balance != null) {
                            if (balance <= account.lowBalanceLimit) {
                                val estimate = repository.calculateUsageEstimate(account.id, balance)
                                NotificationHelper.showLowBalanceAlert(
                                    applicationContext, account, balance, estimate.estimatedDaysLeft
                                )
                            } else {
                                NotificationHelper.cancelLowBalanceAlert(applicationContext, account.id)
                            }
                        }
                    }
                    WidgetUpdater.refreshAll(applicationContext)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(15 * 60 * 1000) // ১৫ মিনিট পর আবার চেক করবে
            }
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "service_channel")
            .setContentTitle("NESCO Tracker Active")
            .setContentText("ব্যালেন্স ব্যাকগ্রাউন্ডে চেক করা হচ্ছে...")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "service_channel",
                "Background Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
