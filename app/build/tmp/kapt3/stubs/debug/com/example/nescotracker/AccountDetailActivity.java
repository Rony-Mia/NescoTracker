package com.example.nescotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\b\u0010\u0013\u001a\u00020\u0011H\u0002J\b\u0010\u0014\u001a\u00020\u0011H\u0002J\b\u0010\u0015\u001a\u00020\u0011H\u0002J\u0012\u0010\u0016\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\u001f\u0010\u001a\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001c\u001a\u00020\nH\u0002\u00a2\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u00020\u00112\b\u0010\u001f\u001a\u0004\u0018\u00010\n2\b\u0010 \u001a\u0004\u0018\u00010!H\u0002\u00a2\u0006\u0002\u0010\"R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u0012\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/example/nescotracker/AccountDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "accountId", "", "binding", "Lcom/example/nescotracker/databinding/ActivityAccountDetailBinding;", "currentAccount", "Lcom/example/nescotracker/data/Account;", "currentBalance", "", "Ljava/lang/Float;", "currentCheckedAt", "Ljava/lang/Long;", "repository", "Lcom/example/nescotracker/data/Repository;", "checkNow", "", "confirmDelete", "loadAccountInfo", "observeHistory", "observeMonthlyUsage", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showEditSettingsDialog", "updateBalanceDisplay", "balance", "lowLimit", "(Ljava/lang/Float;F)V", "updateUsageEstimate", "dailyUsage", "daysLeft", "", "(Ljava/lang/Float;Ljava/lang/Integer;)V", "Companion", "app_debug"})
public final class AccountDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.nescotracker.databinding.ActivityAccountDetailBinding binding;
    private com.example.nescotracker.data.Repository repository;
    private long accountId = -1L;
    @org.jetbrains.annotations.Nullable
    private com.example.nescotracker.data.Account currentAccount;
    @org.jetbrains.annotations.Nullable
    private java.lang.Float currentBalance;
    @org.jetbrains.annotations.Nullable
    private java.lang.Long currentCheckedAt;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_ACCOUNT_ID = "extra_account_id";
    @org.jetbrains.annotations.NotNull
    public static final com.example.nescotracker.AccountDetailActivity.Companion Companion = null;
    
    public AccountDetailActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadAccountInfo() {
    }
    
    private final void observeHistory() {
    }
    
    private final void observeMonthlyUsage() {
    }
    
    private final void updateBalanceDisplay(java.lang.Float balance, float lowLimit) {
    }
    
    private final void updateUsageEstimate(java.lang.Float dailyUsage, java.lang.Integer daysLeft) {
    }
    
    private final void checkNow() {
    }
    
    private final void showEditSettingsDialog() {
    }
    
    private final void confirmDelete() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/nescotracker/AccountDetailActivity$Companion;", "", "()V", "EXTRA_ACCOUNT_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}