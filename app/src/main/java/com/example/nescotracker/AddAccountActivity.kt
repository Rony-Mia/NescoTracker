package com.example.nescotracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nescotracker.data.Account
import com.example.nescotracker.data.Repository
import com.example.nescotracker.databinding.ActivityAddAccountBinding
import kotlinx.coroutines.launch

class AddAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAccountBinding
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository(this)

        binding.etLowBalanceLimit.setText("500")
        binding.etReminderDays.setText("7")

        binding.btnSave.setOnClickListener { saveAccount() }
    }

    private fun saveAccount() {
        val customerNo = binding.etCustomerNo.text.toString().trim()
        val nickname = binding.etNickname.text.toString().trim()
        val lowLimit = binding.etLowBalanceLimit.text.toString().toFloatOrNull() ?: 500f
        val reminderDays = binding.etReminderDays.text.toString().toIntOrNull() ?: 7

        if (customerNo.length < 5) {
            Toast.makeText(this, "সঠিক মিটার/কাস্টমার নম্বর দিন", Toast.LENGTH_SHORT).show()
            return
        }
        if (nickname.isEmpty()) {
            Toast.makeText(this, "মিটারের একটি নাম দিন (যেমন: বাসা)", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnSave.isEnabled = false
        binding.progressBar.visibility = android.view.View.VISIBLE

        lifecycleScope.launch {
            val account = Account(
                customerNo = customerNo,
                nickname = nickname,
                lowBalanceLimit = lowLimit,
                reminderIntervalDays = reminderDays
            )
            val id = repository.addAccount(account)

            // সাথে সাথে একবার ব্যালেন্স চেক করে নেওয়া
            val saved = account.copy(id = id)
            val balance = repository.checkAndSaveBalance(saved)

            binding.progressBar.visibility = android.view.View.GONE
            WidgetUpdater.refreshAll(this@AddAccountActivity)

            if (balance == null) {
                Toast.makeText(
                    this@AddAccountActivity,
                    "মিটার যোগ হয়েছে, কিন্তু ব্যালেন্স চেক করা যায়নি।\nকারণ: ${com.example.nescotracker.NescoChecker.lastError ?: "অজানা"}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@AddAccountActivity,
                    "মিটার যোগ হয়েছে! বর্তমান ব্যালেন্স: ৳$balance",
                    Toast.LENGTH_LONG
                ).show()
            }
            setResult(RESULT_OK)
            finish()
        }
    }
}
