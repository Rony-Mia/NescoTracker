package com.example.nescotracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * প্রতিবার ব্যালেন্স চেক করলে একটি রেকর্ড সেভ হয়।
 * এই হিস্ট্রি থেকেই গ্রাফ আঁকা হয় এবং daily usage/estimated days হিসাব করা হয়।
 */
@Entity(tableName = "balance_records")
data class BalanceRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val accountId: Long,
    val balance: Float,
    val checkedAt: Long = System.currentTimeMillis()
)
