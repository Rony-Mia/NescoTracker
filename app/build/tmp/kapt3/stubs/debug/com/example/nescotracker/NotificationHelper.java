package com.example.nescotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0002J-\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\u0002\u0010\u0015J%\u0010\u0016\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/nescotracker/NotificationHelper;", "", "()V", "CHANNEL_LOW_BALANCE", "", "CHANNEL_REMINDER", "ensureChannels", "", "context", "Landroid/content/Context;", "openAppPendingIntent", "Landroid/app/PendingIntent;", "accountId", "", "showLowBalanceAlert", "account", "Lcom/example/nescotracker/data/Account;", "balance", "", "daysLeft", "", "(Landroid/content/Context;Lcom/example/nescotracker/data/Account;FLjava/lang/Integer;)V", "showRechargeReminder", "(Landroid/content/Context;Lcom/example/nescotracker/data/Account;Ljava/lang/Float;)V", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_LOW_BALANCE = "nesco_low_balance_channel";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_REMINDER = "nesco_reminder_channel";
    @org.jetbrains.annotations.NotNull
    public static final com.example.nescotracker.NotificationHelper INSTANCE = null;
    
    private NotificationHelper() {
        super();
    }
    
    private final void ensureChannels(android.content.Context context) {
    }
    
    private final android.app.PendingIntent openAppPendingIntent(android.content.Context context, long accountId) {
        return null;
    }
    
    public final void showLowBalanceAlert(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.nescotracker.data.Account account, float balance, @org.jetbrains.annotations.Nullable
    java.lang.Integer daysLeft) {
    }
    
    public final void showRechargeReminder(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.nescotracker.data.Account account, @org.jetbrains.annotations.Nullable
    java.lang.Float balance) {
    }
}