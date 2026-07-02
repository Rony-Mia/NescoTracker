package com.example.nescotracker;

/**
 * হোম স্ক্রিন উইজেট আপডেট করার কেন্দ্রীয় জায়গা।
 * সবচেয়ে "জরুরি" মিটার (সবচেয়ে কম ব্যালেন্স, অথবা প্রথম মিটার) দেখানো হয়,
 * সাথে বাকি কয়টা মিটার আছে সেটাও দেখানো হয়।
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/example/nescotracker/WidgetUpdater;", "", "()V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "refreshAll", "", "context", "Landroid/content/Context;", "app_debug"})
public final class WidgetUpdater {
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.nescotracker.WidgetUpdater INSTANCE = null;
    
    private WidgetUpdater() {
        super();
    }
    
    /**
     * ব্যালেন্স চেক/রিফ্রেশ হওয়ার পর যেকোনো জায়গা থেকে কল করা যায়
     */
    public final void refreshAll(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}