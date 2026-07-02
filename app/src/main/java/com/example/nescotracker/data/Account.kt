package com.example.nescotracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * একটি NESCO প্রিপেইড মিটার/অ্যাকাউন্ট।
 * ইউজার একাধিক মিটার যোগ করতে পারবে (বাসা, দোকান, অফিস ইত্যাদি)।
 */
@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerNo: String,          // NESCO কাস্টমার/মিটার নম্বর
    val nickname: String,            // ইউজারের দেওয়া নাম, যেমন "বাসা", "দোকান"
    val lowBalanceLimit: Float = 300f,       // এই ব্যালেন্সের নিচে নামলে alert
    val reminderIntervalDays: Int = 7,       // কতদিন পরপর reminder notification
    val lastReminderSentAt: Long = 0L,
    val isActive: Boolean = true,            // background check চালু/বন্ধ
    val createdAt: Long = System.currentTimeMillis()
)
