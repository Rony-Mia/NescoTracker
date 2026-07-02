package com.example.nescotracker

import android.app.Application

class NescoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // অ্যাপ খোলার সাথে সাথেই সেভ করা থিম (লাইট/ডার্ক/সিস্টেম) অ্যাপ্লাই হয়ে যায়,
        // ফলে কোনো flicker ছাড়াই সঠিক থিমে অ্যাপ ওপেন হয়।
        ThemePrefs.applySavedMode(this)
    }
}
