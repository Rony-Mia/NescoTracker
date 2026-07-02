package com.example.nescotracker;

/**
 * ইউজারের ডার্ক মোড পছন্দ SharedPreferences-এ সেভ রাখে এবং অ্যাপ চালু হওয়ার
 * সাথে সাথে সেটা অ্যাপ্লাই করে দেয় (Splash flicker এড়াতে NescoApp থেকে কল হয়)।
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0006J\u0010\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J\u000e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/nescotracker/ThemePrefs;", "", "()V", "KEY_MODE", "", "MODE_DARK", "", "MODE_LIGHT", "MODE_SYSTEM", "PREFS_NAME", "applySavedMode", "", "context", "Landroid/content/Context;", "getSavedMode", "isDarkActive", "", "isSystemInDarkMode", "setMode", "mode", "toDelegateMode", "toggle", "app_debug"})
public final class ThemePrefs {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "nesco_theme_prefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_MODE = "dark_mode_option";
    public static final int MODE_SYSTEM = 0;
    public static final int MODE_LIGHT = 1;
    public static final int MODE_DARK = 2;
    @org.jetbrains.annotations.NotNull
    public static final com.example.nescotracker.ThemePrefs INSTANCE = null;
    
    private ThemePrefs() {
        super();
    }
    
    public final int getSavedMode(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    public final void applySavedMode(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    public final void setMode(@org.jetbrains.annotations.NotNull
    android.content.Context context, int mode) {
    }
    
    /**
     * ডার্ক/লাইট এর মধ্যে টগল করে (সিস্টেম মোডে থাকলে বর্তমান অবস্থা দেখে টগল করে)
     */
    public final int toggle(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    public final boolean isDarkActive(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    private final boolean isSystemInDarkMode(android.content.Context context) {
        return false;
    }
    
    private final int toDelegateMode(int mode) {
        return 0;
    }
}