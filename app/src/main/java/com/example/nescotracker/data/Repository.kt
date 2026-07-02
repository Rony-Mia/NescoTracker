package com.example.nescotracker.data

import android.content.Context
import com.example.nescotracker.NescoChecker
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.max

/**
 * ফলাফল যা "কয়দিন চলবে" হিসাব করার পর পাওয়া যায়।
 */
data class UsageEstimate(
    val dailyUsage: Float?,       // গড়ে প্রতিদিন কত টাকা খরচ হচ্ছে
    val estimatedDaysLeft: Int?   // বর্তমান ব্যালেন্স দিয়ে আর কতদিন চলবে (আনুমানিক)
)

/** ব্যালেন্স হঠাৎ বেড়ে গেলে সেটাকে একটা "রিচার্জ" ইভেন্ট হিসেবে ধরা হয় */
data class RechargeEvent(
    val date: Long,
    val amount: Float,
    val balanceAfter: Float
)

/** একটি নির্দিষ্ট মাসে মোট কত টাকা খরচ হয়েছে (আনুমানিক) */
data class MonthlyUsage(
    val monthLabel: String,
    val yearMonthKey: String,
    val usage: Float
)

class Repository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val accountDao = db.accountDao()
    private val balanceRecordDao = db.balanceRecordDao()

    fun allAccountsFlow() = accountDao.getAllAccountsFlow()
    fun latestBalancesFlow() = balanceRecordDao.getLatestForAllAccountsFlow()
    fun historyFlow(accountId: Long) = balanceRecordDao.getHistoryFlow(accountId)

    suspend fun getAccount(accountId: Long) = accountDao.getAccountById(accountId)
    suspend fun getLatestRecord(accountId: Long) = balanceRecordDao.getLatest(accountId)
    suspend fun getActiveAccounts() = accountDao.getActiveAccounts()

    suspend fun addAccount(account: Account): Long = accountDao.insert(account)
    suspend fun updateAccount(account: Account) = accountDao.update(account)
    suspend fun deleteAccount(account: Account) = accountDao.delete(account)
    suspend fun markReminderSent(accountId: Long) =
        accountDao.updateLastReminderSent(accountId, System.currentTimeMillis())

    /**
     * ওয়েবসাইট থেকে সরাসরি ব্যালেন্স চেক করে এবং হিস্ট্রিতে সেভ করে।
     * রিটার্ন করে বর্তমান ব্যালেন্স (null হলে চেক ব্যর্থ হয়েছে)।
     */
    suspend fun checkAndSaveBalance(account: Account): Float? {
        val balance = NescoChecker.checkBalance(account.customerNo) ?: return null
        balanceRecordDao.insert(
            BalanceRecord(accountId = account.id, balance = balance)
        )
        // পুরনো (৯০ দিনের বেশি) ডেটা পরিষ্কার রাখা
        val cutoff = System.currentTimeMillis() - (90L * 24 * 60 * 60 * 1000)
        balanceRecordDao.deleteOlderThan(cutoff)
        return balance
    }

    /**
     * সাম্প্রতিক রেকর্ডগুলো দেখে গড় দৈনিক খরচ ও আনুমানিক কয়দিন চলবে হিসাব করে।
     * রিচার্জ করলে ব্যালেন্স হঠাৎ বেড়ে যায় বলে, শুধু "কমতে থাকা" ধারাবাহিক রেকর্ডগুলো ধরা হয়।
     */
    suspend fun calculateUsageEstimate(accountId: Long, currentBalance: Float): UsageEstimate {
        val recent = balanceRecordDao.getRecent(accountId, 30).sortedBy { it.checkedAt }
        if (recent.size < 2) return UsageEstimate(null, null)

        // শুধু কমতে থাকা ধারাবাহিক অংশ বের করা (সর্বশেষ রিচার্জের পর থেকে)
        val declining = mutableListOf(recent.last())
        for (i in recent.size - 2 downTo 0) {
            val cur = recent[i]
            if (cur.balance >= declining.first().balance) {
                declining.add(0, cur)
            } else {
                break
            }
        }

        if (declining.size < 2) return UsageEstimate(null, null)

        val first = declining.first()
        val last = declining.last()
        val daysBetween = max(1f, (last.checkedAt - first.checkedAt) / (1000f * 60 * 60 * 24))
        val usedAmount = first.balance - last.balance

        if (usedAmount <= 0f) return UsageEstimate(null, null)

        val dailyUsage = usedAmount / daysBetween
        val daysLeft = if (dailyUsage > 0f) (currentBalance / dailyUsage).toInt() else null

        return UsageEstimate(dailyUsage, daysLeft)
    }

    /**
     * ব্যালেন্স হিস্ট্রি থেকে রিচার্জ ইভেন্টগুলো বের করে (যখনই ব্যালেন্স আগের চেয়ে বেড়েছে,
     * সেটাই রিচার্জ ধরা হয়), নতুন থেকে পুরনো ক্রমে সাজানো।
     */
    fun rechargeHistoryFlow(accountId: Long) = balanceRecordDao.getHistoryFlow(accountId).map { history ->
        val sorted = history.sortedBy { it.checkedAt }
        val events = mutableListOf<RechargeEvent>()
        for (i in 1 until sorted.size) {
            val prev = sorted[i - 1]
            val cur = sorted[i]
            val diff = cur.balance - prev.balance
            if (diff > 0.5f) { // সামান্য উঠানামা (রাউন্ডিং) রিচার্জ হিসেবে গণনা না করার জন্য
                events.add(RechargeEvent(date = cur.checkedAt, amount = diff, balanceAfter = cur.balance))
            }
        }
        events.sortedByDescending { it.date }
    }

    /**
     * সাম্প্রতিক ৬ মাসের প্রতি মাসে আনুমানিক মোট খরচ হিসাব করে (রিচার্জ বাদ দিয়ে, শুধু কমতে
     * থাকা ব্যালেন্সের যোগফল ধরা হয়) — মাসিক খরচের রিপোর্ট/গ্রাফের জন্য ব্যবহৃত হয়।
     */
    fun monthlyUsageFlow(accountId: Long) = balanceRecordDao.getHistoryFlow(accountId).map { history ->
        val sorted = history.sortedBy { it.checkedAt }
        val monthFormat = SimpleDateFormat("MMM yy", Locale.ENGLISH)
        val keyFormat = SimpleDateFormat("yyyyMM", Locale.ENGLISH)
        val usageByMonth = LinkedHashMap<String, Float>()
        val labelByMonth = LinkedHashMap<String, String>()

        for (i in 1 until sorted.size) {
            val prev = sorted[i - 1]
            val cur = sorted[i]
            val used = prev.balance - cur.balance
            if (used > 0f) { // শুধু খরচ (কমতে থাকা) ধরা হয়, রিচার্জের বাড়তি বাদ
                val cal = Calendar.getInstance().apply { timeInMillis = cur.checkedAt }
                val key = keyFormat.format(cal.time)
                usageByMonth[key] = (usageByMonth[key] ?: 0f) + used
                labelByMonth[key] = monthFormat.format(cal.time)
            }
        }

        usageByMonth.entries
            .sortedBy { it.key }
            .takeLast(6)
            .map { (key, usage) -> MonthlyUsage(labelByMonth[key] ?: key, key, usage) }
    }
}
