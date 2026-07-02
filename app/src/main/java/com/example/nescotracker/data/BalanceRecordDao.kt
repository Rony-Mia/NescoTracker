package com.example.nescotracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceRecordDao {

    @Insert
    suspend fun insert(record: BalanceRecord)

    @Query("SELECT * FROM balance_records WHERE accountId = :accountId ORDER BY checkedAt ASC")
    fun getHistoryFlow(accountId: Long): Flow<List<BalanceRecord>>

    @Query("SELECT * FROM balance_records WHERE accountId = :accountId ORDER BY checkedAt DESC LIMIT 1")
    suspend fun getLatest(accountId: Long): BalanceRecord?

    @Query("SELECT * FROM balance_records WHERE accountId = :accountId ORDER BY checkedAt DESC LIMIT :limit")
    suspend fun getRecent(accountId: Long, limit: Int): List<BalanceRecord>

    // সব অ্যাকাউন্টের সর্বশেষ ব্যালেন্স রেকর্ড (ড্যাশবোর্ড লিস্টের জন্য)
    @Query("""
        SELECT * FROM balance_records
        WHERE id IN (
            SELECT MAX(id) FROM balance_records GROUP BY accountId
        )
    """)
    fun getLatestForAllAccountsFlow(): Flow<List<BalanceRecord>>

    // ৯০ দিনের বেশি পুরনো ডেটা মুছে ফেলা, ডেটাবেজ হালকা রাখতে
    @Query("DELETE FROM balance_records WHERE checkedAt < :cutoffTimestamp")
    suspend fun deleteOlderThan(cutoffTimestamp: Long)
}
