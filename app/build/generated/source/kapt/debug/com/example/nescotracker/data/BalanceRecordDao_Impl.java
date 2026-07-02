package com.example.nescotracker.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BalanceRecordDao_Impl implements BalanceRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BalanceRecord> __insertionAdapterOfBalanceRecord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOlderThan;

  public BalanceRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBalanceRecord = new EntityInsertionAdapter<BalanceRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `balance_records` (`id`,`accountId`,`balance`,`checkedAt`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BalanceRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAccountId());
        statement.bindDouble(3, entity.getBalance());
        statement.bindLong(4, entity.getCheckedAt());
      }
    };
    this.__preparedStmtOfDeleteOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM balance_records WHERE checkedAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BalanceRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBalanceRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOlderThan(final long cutoffTimestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoffTimestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BalanceRecord>> getHistoryFlow(final long accountId) {
    final String _sql = "SELECT * FROM balance_records WHERE accountId = ? ORDER BY checkedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"balance_records"}, new Callable<List<BalanceRecord>>() {
      @Override
      @NonNull
      public List<BalanceRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCheckedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "checkedAt");
          final List<BalanceRecord> _result = new ArrayList<BalanceRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BalanceRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final float _tmpBalance;
            _tmpBalance = _cursor.getFloat(_cursorIndexOfBalance);
            final long _tmpCheckedAt;
            _tmpCheckedAt = _cursor.getLong(_cursorIndexOfCheckedAt);
            _item = new BalanceRecord(_tmpId,_tmpAccountId,_tmpBalance,_tmpCheckedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLatest(final long accountId,
      final Continuation<? super BalanceRecord> $completion) {
    final String _sql = "SELECT * FROM balance_records WHERE accountId = ? ORDER BY checkedAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BalanceRecord>() {
      @Override
      @Nullable
      public BalanceRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCheckedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "checkedAt");
          final BalanceRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final float _tmpBalance;
            _tmpBalance = _cursor.getFloat(_cursorIndexOfBalance);
            final long _tmpCheckedAt;
            _tmpCheckedAt = _cursor.getLong(_cursorIndexOfCheckedAt);
            _result = new BalanceRecord(_tmpId,_tmpAccountId,_tmpBalance,_tmpCheckedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecent(final long accountId, final int limit,
      final Continuation<? super List<BalanceRecord>> $completion) {
    final String _sql = "SELECT * FROM balance_records WHERE accountId = ? ORDER BY checkedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BalanceRecord>>() {
      @Override
      @NonNull
      public List<BalanceRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCheckedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "checkedAt");
          final List<BalanceRecord> _result = new ArrayList<BalanceRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BalanceRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final float _tmpBalance;
            _tmpBalance = _cursor.getFloat(_cursorIndexOfBalance);
            final long _tmpCheckedAt;
            _tmpCheckedAt = _cursor.getLong(_cursorIndexOfCheckedAt);
            _item = new BalanceRecord(_tmpId,_tmpAccountId,_tmpBalance,_tmpCheckedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BalanceRecord>> getLatestForAllAccountsFlow() {
    final String _sql = "\n"
            + "        SELECT * FROM balance_records\n"
            + "        WHERE id IN (\n"
            + "            SELECT MAX(id) FROM balance_records GROUP BY accountId\n"
            + "        )\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"balance_records"}, new Callable<List<BalanceRecord>>() {
      @Override
      @NonNull
      public List<BalanceRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCheckedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "checkedAt");
          final List<BalanceRecord> _result = new ArrayList<BalanceRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BalanceRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final float _tmpBalance;
            _tmpBalance = _cursor.getFloat(_cursorIndexOfBalance);
            final long _tmpCheckedAt;
            _tmpCheckedAt = _cursor.getLong(_cursorIndexOfCheckedAt);
            _item = new BalanceRecord(_tmpId,_tmpAccountId,_tmpBalance,_tmpCheckedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
