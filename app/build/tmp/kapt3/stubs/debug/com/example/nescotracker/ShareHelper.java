package com.example.nescotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J/\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/example/nescotracker/ShareHelper;", "", "()V", "timeFormat", "Ljava/text/SimpleDateFormat;", "shareBalance", "", "context", "Landroid/content/Context;", "account", "Lcom/example/nescotracker/data/Account;", "balance", "", "checkedAt", "", "(Landroid/content/Context;Lcom/example/nescotracker/data/Account;Ljava/lang/Float;Ljava/lang/Long;)V", "app_debug"})
public final class ShareHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.text.SimpleDateFormat timeFormat = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.nescotracker.ShareHelper INSTANCE = null;
    
    private ShareHelper() {
        super();
    }
    
    /**
     * WhatsApp, SMS বা যেকোনো অ্যাপে ব্যালেন্স তথ্য শেয়ার করার জন্য chooser খোলে
     */
    public final void shareBalance(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.nescotracker.data.Account account, @org.jetbrains.annotations.Nullable
    java.lang.Float balance, @org.jetbrains.annotations.Nullable
    java.lang.Long checkedAt) {
    }
}