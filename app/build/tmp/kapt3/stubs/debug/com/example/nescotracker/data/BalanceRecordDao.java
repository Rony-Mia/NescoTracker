package com.example.nescotracker.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\u0005H\'J\u001b\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\'\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/example/nescotracker/data/BalanceRecordDao;", "", "deleteOlderThan", "", "cutoffTimestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHistoryFlow", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/nescotracker/data/BalanceRecord;", "accountId", "getLatest", "getLatestForAllAccountsFlow", "getRecent", "limit", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "record", "(Lcom/example/nescotracker/data/BalanceRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface BalanceRecordDao {
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.example.nescotracker.data.BalanceRecord record, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM balance_records WHERE accountId = :accountId ORDER BY checkedAt ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.nescotracker.data.BalanceRecord>> getHistoryFlow(long accountId);
    
    @androidx.room.Query(value = "SELECT * FROM balance_records WHERE accountId = :accountId ORDER BY checkedAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getLatest(long accountId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.nescotracker.data.BalanceRecord> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM balance_records WHERE accountId = :accountId ORDER BY checkedAt DESC LIMIT :limit")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRecent(long accountId, int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.nescotracker.data.BalanceRecord>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM balance_records\n        WHERE id IN (\n            SELECT MAX(id) FROM balance_records GROUP BY accountId\n        )\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.nescotracker.data.BalanceRecord>> getLatestForAllAccountsFlow();
    
    @androidx.room.Query(value = "DELETE FROM balance_records WHERE checkedAt < :cutoffTimestamp")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOlderThan(long cutoffTimestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}