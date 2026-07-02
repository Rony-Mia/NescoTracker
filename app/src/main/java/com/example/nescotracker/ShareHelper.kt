package com.example.nescotracker

import android.content.Context
import android.content.Intent
import com.example.nescotracker.data.Account
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ShareHelper {

    private val timeFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)

    /** WhatsApp, SMS বা যেকোনো অ্যাপে ব্যালেন্স তথ্য শেয়ার করার জন্য chooser খোলে */
    fun shareBalance(context: Context, account: Account, balance: Float?, checkedAt: Long?) {
        val text = buildString {
            append("⚡ NESCO মিটার ব্যালেন্স\n\n")
            append("মিটার: ${account.nickname}\n")
            append("গ্রাহক নং: ${account.customerNo}\n")
            if (balance != null) {
                append("বর্তমান ব্যালেন্স: ৳${"%.2f".format(balance)}\n")
                if (balance <= account.lowBalanceLimit) {
                    append("⚠️ ব্যালেন্স কম, শীঘ্রই রিচার্জ প্রয়োজন\n")
                }
            } else {
                append("ব্যালেন্স এখনো চেক করা হয়নি\n")
            }
            if (checkedAt != null) {
                append("সময়: ${timeFormat.format(Date(checkedAt))}\n")
            }
            append("\nNESCO Tracker অ্যাপ দিয়ে পাঠানো হয়েছে")
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, "ব্যালেন্স শেয়ার করুন"))
    }
}
