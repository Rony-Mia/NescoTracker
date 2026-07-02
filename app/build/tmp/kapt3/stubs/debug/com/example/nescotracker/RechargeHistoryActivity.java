package com.example.nescotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/nescotracker/RechargeHistoryActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "accountId", "", "adapter", "Lcom/example/nescotracker/RechargeAdapter;", "binding", "Lcom/example/nescotracker/databinding/ActivityRechargeHistoryBinding;", "repository", "Lcom/example/nescotracker/data/Repository;", "observeHistory", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class RechargeHistoryActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.nescotracker.databinding.ActivityRechargeHistoryBinding binding;
    private com.example.nescotracker.data.Repository repository;
    private com.example.nescotracker.RechargeAdapter adapter;
    private long accountId = -1L;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_ACCOUNT_ID = "extra_account_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_NICKNAME = "extra_nickname";
    @org.jetbrains.annotations.NotNull
    public static final com.example.nescotracker.RechargeHistoryActivity.Companion Companion = null;
    
    public RechargeHistoryActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void observeHistory() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/nescotracker/RechargeHistoryActivity$Companion;", "", "()V", "EXTRA_ACCOUNT_ID", "", "EXTRA_NICKNAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}