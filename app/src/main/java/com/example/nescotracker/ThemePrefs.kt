package com.example.nescotracker

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * ইউজারের ডার্ক মোড পছন্দ SharedPreferences-এ সেভ রাখে এবং অ্যাপ চালু হওয়ার
 * সাথে সাথে সেটা অ্যাপ্লাই করে দেয় (Splash flicker এড়াতে NescoApp থেকে কল হয়)।
 */
object ThemePrefs {
    private const val PREFS_NAME = "nesco_theme_prefs"
    private const val KEY_MODE = "dark_mode_option" // 0=System, 1=Light, 2=Dark

    const val MODE_SYSTEM = 0
    const val MODE_LIGHT = 1
    const val MODE_DARK = 2

    fun getSavedMode(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_MODE, MODE_SYSTEM)
    }

    fun applySavedMode(context: Context) {
        AppCompatDelegate.setDefaultNightMode(toDelegateMode(getSavedMode(context)))
    }

    fun setMode(context: Context, mode: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_MODE, mode).apply()
        AppCompatDelegate.setDefaultNightMode(toDelegateMode(mode))
    }

    /** ডার্ক/লাইট এর মধ্যে টগল করে (সিস্টেম মোডে থাকলে বর্তমান অবস্থা দেখে টগল করে) */
    fun toggle(context: Context): Int {
        val isCurrentlyDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ||
            (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM &&
                isSystemInDarkMode(context))
        val newMode = if (isCurrentlyDark) MODE_LIGHT else MODE_DARK
        setMode(context, newMode)
        return newMode
    }

    fun isDarkActive(context: Context): Boolean {
        val nightModeFlags = context.resources.configuration.uiMode and
            android.content.res.Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES
    }

    private fun isSystemInDarkMode(context: Context): Boolean = isDarkActive(context)

    private fun toDelegateMode(mode: Int): Int = when (mode) {
        MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}
