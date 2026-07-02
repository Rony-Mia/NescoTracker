package com.example.nescotracker.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class AccountDao_Impl implements AccountDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Account> __insertionAdapterOfAccount;

  private final EntityDeletionOrUpdateAdapter<Account> __deletionAdapterOfAccount;

  private final EntityDeletionOrUpdateAdapter<Account> __updateAdapterOfAccount;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastReminderSent;

  public AccountDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAccount = new EntityInsertionAdapter<Account>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `accounts` (`id`,`customerNo`,`nickname`,`lowBalanceLimit`,`reminderIntervalDays`,`lastReminderSentAt`,`isActive`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Account entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getCustomerNo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCustomerNo());
        }
        if (entity.getNickname() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNickname());
        }
        statement.bindDouble(4, entity.getLowBalanceLimit());
        statement.bindLong(5, entity.getReminderIntervalDays());
        statement.bindLong(6, entity.getLastReminderSentAt());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfAccount = new EntityDeletionOrUpdateAdapter<Account>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `accounts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Account entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAccount = new EntityDeletionOrUpdateAdapter<Account>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `accounts` SET `id` = ?,`customerNo` = ?,`nickname` = ?,`lowBalanceLimit` = ?,`reminderIntervalDays` = ?,`lastReminderSentAt` = ?,`isActive` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Account entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getCustomerNo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCustomerNo());
        }
        if (entity.getNickname() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNickname());
        }
        statement.bindDouble(4, entity.getLowBalanceLimit());
        statement.bindLong(5, entity.getReminderIntervalDays());
        statement.bindLong(6, entity.getLastReminderSentAt());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateLastReminderSent = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE accounts SET lastReminderSentAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Account account, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAccount.insertAndReturnId(account);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Account account, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAccount.handle(account);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Account account, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAccount.handle(account);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastReminderSent(final long accountId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastReminderSent.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, accountId);
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
          __preparedStmtOfUpdateLastReminderSent.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Account>> getAllAccountsFlow() {
    final String _sql = "SELECT * FROM accounts ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"accounts"}, new Callable<List<Account>>() {
      @Override
      @NonNull
      public List<Account> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerNo = CursorUtil.getColumnIndexOrThrow(_cursor, "customerNo");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfLowBalanceLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "lowBalanceLimit");
          final int _cursorIndexOfReminderIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderIntervalDays");
          final int _cursorIndexOfLastReminderSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReminderSentAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Account> _result = new ArrayList<Account>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Account _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCustomerNo;
            if (_cursor.isNull(_cursorIndexOfCustomerNo)) {
              _tmpCustomerNo = null;
            } else {
              _tmpCustomerNo = _cursor.getString(_cursorIndexOfCustomerNo);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final float _tmpLowBalanceLimit;
            _tmpLowBalanceLimit = _cursor.getFloat(_cursorIndexOfLowBalanceLimit);
            final int _tmpReminderIntervalDays;
            _tmpReminderIntervalDays = _cursor.getInt(_cursorIndexOfReminderIntervalDays);
            final long _tmpLastReminderSentAt;
            _tmpLastReminderSentAt = _cursor.getLong(_cursorIndexOfLastReminderSentAt);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Account(_tmpId,_tmpCustomerNo,_tmpNickname,_tmpLowBalanceLimit,_tmpReminderIntervalDays,_tmpLastReminderSentAt,_tmpIsActive,_tmpCreatedAt);
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
  public Object getActiveAccounts(final Continuation<? super List<Account>> $completion) {
    final String _sql = "SELECT * FROM accounts WHERE isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Account>>() {
      @Override
      @NonNull
      public List<Account> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerNo = CursorUtil.getColumnIndexOrThrow(_cursor, "customerNo");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfLowBalanceLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "lowBalanceLimit");
          final int _cursorIndexOfReminderIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderIntervalDays");
          final int _cursorIndexOfLastReminderSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReminderSentAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Account> _result = new ArrayList<Account>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Account _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCustomerNo;
            if (_cursor.isNull(_cursorIndexOfCustomerNo)) {
              _tmpCustomerNo = null;
            } else {
              _tmpCustomerNo = _cursor.getString(_cursorIndexOfCustomerNo);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final float _tmpLowBalanceLimit;
            _tmpLowBalanceLimit = _cursor.getFloat(_cursorIndexOfLowBalanceLimit);
            final int _tmpReminderIntervalDays;
            _tmpReminderIntervalDays = _cursor.getInt(_cursorIndexOfReminderIntervalDays);
            final long _tmpLastReminderSentAt;
            _tmpLastReminderSentAt = _cursor.getLong(_cursorIndexOfLastReminderSentAt);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Account(_tmpId,_tmpCustomerNo,_tmpNickname,_tmpLowBalanceLimit,_tmpReminderIntervalDays,_tmpLastReminderSentAt,_tmpIsActive,_tmpCreatedAt);
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
  public Object getAccountById(final long accountId,
      final Continuation<? super Account> $completion) {
    final String _sql = "SELECT * FROM accounts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Account>() {
      @Override
      @Nullable
      public Account call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerNo = CursorUtil.getColumnIndexOrThrow(_cursor, "customerNo");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfLowBalanceLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "lowBalanceLimit");
          final int _cursorIndexOfReminderIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderIntervalDays");
          final int _cursorIndexOfLastReminderSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReminderSentAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Account _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCustomerNo;
            if (_cursor.isNull(_cursorIndexOfCustomerNo)) {
              _tmpCustomerNo = null;
            } else {
              _tmpCustomerNo = _cursor.getString(_cursorIndexOfCustomerNo);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final float _tmpLowBalanceLimit;
            _tmpLowBalanceLimit = _cursor.getFloat(_cursorIndexOfLowBalanceLimit);
            final int _tmpReminderIntervalDays;
            _tmpReminderIntervalDays = _cursor.getInt(_cursorIndexOfReminderIntervalDays);
            final long _tmpLastReminderSentAt;
            _tmpLastReminderSentAt = _cursor.getLong(_cursorIndexOfLastReminderSentAt);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Account(_tmpId,_tmpCustomerNo,_tmpNickname,_tmpLowBalanceLimit,_tmpReminderIntervalDays,_tmpLastReminderSentAt,_tmpIsActive,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
