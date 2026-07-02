package com.example.nescotracker;

/**
 * হোম স্ক্রিন উইজেট: অ্যাপ না খুলেই সবচেয়ে জরুরি মিটারের ব্যালেন্স দেখা যায়।
 * প্রকৃত ডেটা লোডিং WidgetUpdater-এ হয়, যেটা অ্যাপের যেকোনো ব্যালেন্স-রিফ্রেশের পরও কল হয়।
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016\u00a8\u0006\f"}, d2 = {"Lcom/example/nescotracker/BalanceWidgetProvider;", "Landroid/appwidget/AppWidgetProvider;", "()V", "onEnabled", "", "context", "Landroid/content/Context;", "onUpdate", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "app_debug"})
public final class BalanceWidgetProvider extends android.appwidget.AppWidgetProvider {
    
    public BalanceWidgetProvider() {
        super();
    }
    
    @java.lang.Override
    public void onUpdate(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.appwidget.AppWidgetManager appWidgetManager, @org.jetbrains.annotations.NotNull
    int[] appWidgetIds) {
    }
    
    @java.lang.Override
    public void onEnabled(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}