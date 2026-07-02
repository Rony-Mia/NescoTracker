package com.example.nescotracker

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nescotracker.data.Account
import com.example.nescotracker.data.Repository
import com.example.nescotracker.databinding.ActivityAccountDetailBinding
import kotlinx.coroutines.launch

class AccountDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountDetailBinding
    private lateinit var repository: Repository
    private var accountId: Long = -1
    private var currentAccount: Account? = null
    private var currentBalance: Float? = null
    private var currentCheckedAt: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository(this)
        accountId = intent.getLongExtra(EXTRA_ACCOUNT_ID, -1)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "মিটার ডিটেইলস"

        if (accountId == -1L) {
            finish()
            return
        }

        binding.btnCheckNow.setOnClickListener { checkNow() }
        binding.btnEditSettings.setOnClickListener { showEditSettingsDialog() }
        binding.btnDelete.setOnClickListener { confirmDelete() }
        binding.btnShareBalance.setOnClickListener {
            currentAccount?.let { ShareHelper.shareBalance(this, it, currentBalance, currentCheckedAt) }
        }
        binding.btnRechargeHistory.setOnClickListener {
            val intent = Intent(this, RechargeHistoryActivity::class.java)
            intent.putExtra(RechargeHistoryActivity.EXTRA_ACCOUNT_ID, accountId)
            intent.putExtra(RechargeHistoryActivity.EXTRA_NICKNAME, currentAccount?.nickname ?: "মিটার")
            startActivity(intent)
        }

        observeHistory()
        observeMonthlyUsage()
        loadAccountInfo()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun loadAccountInfo() {
        lifecycleScope.launch {
            val account = repository.getAccount(accountId) ?: return@launch
            currentAccount = account
            binding.tvNickname.text = account.nickname
            binding.tvCustomerNo.text = "মিটার নং: ${account.customerNo}"
            binding.tvLowLimitInfo.text = "লো-ব্যালেন্স লিমিট: ৳${account.lowBalanceLimit.toInt()}"
            binding.tvReminderInfo.text = "রিমাইন্ডার: প্রতি ${account.reminderIntervalDays} দিনে"

            val latest = repository.getLatestRecord(accountId)
            currentBalance = latest?.balance
            currentCheckedAt = latest?.checkedAt
            updateBalanceDisplay(latest?.balance, account.lowBalanceLimit)
            if (latest != null) {
                val estimate = repository.calculateUsageEstimate(accountId, latest.balance)
                updateUsageEstimate(estimate.dailyUsage, estimate.estimatedDaysLeft)
            }
        }
    }

    private fun observeHistory() {
        lifecycleScope.launch {
            repository.historyFlow(accountId).collect { history ->
                binding.chartView.setData(history)
                if (history.isNotEmpty()) {
                    val account = repository.getAccount(accountId)
                    currentAccount = account
                    currentBalance = history.last().balance
                    currentCheckedAt = history.last().checkedAt
                    updateBalanceDisplay(history.last().balance, account?.lowBalanceLimit ?: 500f)
                    val estimate = repository.calculateUsageEstimate(accountId, history.last().balance)
                    updateUsageEstimate(estimate.dailyUsage, estimate.estimatedDaysLeft)
                }
            }
        }
    }

    private fun observeMonthlyUsage() {
        lifecycleScope.launch {
            repository.monthlyUsageFlow(accountId).collect { monthly ->
                binding.barChartView.setData(monthly, ThemePrefs.isDarkActive(this@AccountDetailActivity))
            }
        }
    }

    private fun updateBalanceDisplay(balance: Float?, lowLimit: Float) {
        if (balance == null) {
            binding.tvBalance.text = "চেক করা হয়নি"
            return
        }
        binding.tvBalance.text = "৳${"%.2f".format(balance)}"
        val isLow = balance <= lowLimit
        binding.tvBalance.setTextColor(Color.parseColor(if (isLow) "#FF6B6B" else "#FFFFFF"))
        binding.tvStatusTag.text = if (isLow) "⚠️ ব্যালেন্স কম, রিচার্জ করুন" else "✅ ব্যালেন্স ঠিক আছে"
        binding.tvStatusTag.setBackgroundResource(
            if (isLow) R.drawable.bg_tag_low_balance else R.drawable.bg_tag_ok
        )
        binding.tvStatusTag.setTextColor(Color.parseColor(if (isLow) "#FF6B6B" else "#FFFFFF"))
    }

    private fun updateUsageEstimate(dailyUsage: Float?, daysLeft: Int?) {
        if (dailyUsage == null) {
            binding.tvUsageEstimate.text = "দৈনিক গড় খরচ হিসাব করার জন্য আরও কিছুদিনের ডেটা দরকার"
            return
        }
        val daysText = if (daysLeft != null) "আনুমানিক আর $daysLeft দিন চলবে" else ""
        binding.tvUsageEstimate.text =
            "গড়ে প্রতিদিন খরচ: ৳${"%.2f".format(dailyUsage)}। $daysText"
    }

    private fun checkNow() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        lifecycleScope.launch {
            val account = repository.getAccount(accountId)
            if (account != null) {
                val balance = repository.checkAndSaveBalance(account)
                binding.progressBar.visibility = android.view.View.GONE
                WidgetUpdater.refreshAll(this@AccountDetailActivity)
                if (balance == null) {
                    Toast.makeText(
                        this@AccountDetailActivity,
                        "চেক করা যায়নি।\nকারণ: ${NescoChecker.lastError ?: "অজানা"}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@AccountDetailActivity, "আপডেট হয়েছে: ৳$balance", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showEditSettingsDialog() {
        lifecycleScope.launch {
            val account = repository.getAccount(accountId) ?: return@launch

            val container = LinearLayout(this@AccountDetailActivity).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(60, 30, 60, 10)
            }
            val etLimit = EditText(this@AccountDetailActivity).apply {
                hint = "লো ব্যালেন্স লিমিট (টাকা)"
                setText(account.lowBalanceLimit.toInt().toString())
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }
            val etReminder = EditText(this@AccountDetailActivity).apply {
                hint = "রিমাইন্ডার (কতদিন পরপর)"
                setText(account.reminderIntervalDays.toString())
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }
            container.addView(etLimit)
            container.addView(etReminder)

            AlertDialog.Builder(this@AccountDetailActivity)
                .setTitle("সেটিংস পরিবর্তন করুন")
                .setView(container)
                .setPositiveButton("সেভ করুন") { _, _ ->
                    val newLimit = etLimit.text.toString().toFloatOrNull() ?: account.lowBalanceLimit
                    val newReminder = etReminder.text.toString().toIntOrNull() ?: account.reminderIntervalDays
                    lifecycleScope.launch {
                        repository.updateAccount(
                            account.copy(lowBalanceLimit = newLimit, reminderIntervalDays = newReminder)
                        )
                        loadAccountInfo()
                        Toast.makeText(this@AccountDetailActivity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("বাতিল", null)
                .show()
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("মিটার মুছে ফেলবেন?")
            .setMessage("এই মিটার এবং এর সব হিস্ট্রি চিরতরে মুছে যাবে।")
            .setPositiveButton("মুছে ফেলুন") { _, _ ->
                lifecycleScope.launch {
                    val account = repository.getAccount(accountId)
                    if (account != null) {
                        repository.deleteAccount(account)
                    }
                    finish()
                }
            }
            .setNegativeButton("বাতিল", null)
            .show()
    }

    companion object {
        const val EXTRA_ACCOUNT_ID = "extra_account_id"
    }
}
