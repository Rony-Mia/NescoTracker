package com.example.nescotracker

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException

/**
 * NESCO ওয়েবসাইট থেকে প্রিপেইড ব্যালেন্স বের করার মূল লজিক।
 */
object NescoChecker {
    private const val BASE_URL = "https://customer.nesco.gov.bd/pre/panel"
    private const val TAG = "NescoChecker"
    private const val TIMEOUT_MS = 20000

    // চেক ব্যর্থ হলে আসল কারণ এখানে থাকবে, UI থেকে দেখানো যাবে
    var lastError: String? = null
        private set

    suspend fun checkBalance(customerNo: String): Float? = withContext(Dispatchers.IO) {
        lastError = null
        try {
            // ১. প্রথমে পেজের HTML আনা (GET request), CSRF টোকেন বের করার জন্য
            val response = Jsoup.connect(BASE_URL)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36")
                .header("Accept-Language", "bn-BD,bn;q=0.9,en-US;q=0.8,en;q=0.7")
                .timeout(TIMEOUT_MS)
                .followRedirects(true)
                .method(org.jsoup.Connection.Method.GET)
                .execute()

            val doc = response.parse()
            val csrfToken = doc.select("meta[name=csrf-token]").attr("content")
            Log.d(TAG, "GET status=${response.statusCode()} csrfToken empty=${csrfToken.isEmpty()}")

            if (csrfToken.isEmpty()) {
                lastError = "CSRF টোকেন পাওয়া যায়নি (status: ${response.statusCode()}). সাইটের স্ট্রাকচার পরিবর্তন হয়ে থাকতে পারে।"
                Log.e(TAG, lastError!!)
                return@withContext null
            }

            // ২. গ্রাহক নম্বর দিয়ে পোস্ট রিকোয়েস্ট করা
            val postConn = Jsoup.connect(BASE_URL)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36")
                .header("Accept-Language", "bn-BD,bn;q=0.9,en-US;q=0.8,en;q=0.7")
                .referrer(BASE_URL)
                .timeout(TIMEOUT_MS)
                .followRedirects(true)
                .data("_token", csrfToken)
                .data("cust_no", customerNo)
                .data("submit", "রিচার্জ হিস্ট্রি")
                .cookies(response.cookies())

            val postResponse = postConn.method(org.jsoup.Connection.Method.POST).execute()
            val postDoc = postResponse.parse()
            Log.d(TAG, "POST status=${postResponse.statusCode()} bodyLength=${postDoc.body().text().length}")

            // ৩. ফলাফলের HTML থেকে ব্যালেন্স বের করা
            val balanceLabel = postDoc.select("label:contains(অবশিষ্ট ব্যালেন্স)").first()
            if (balanceLabel == null) {
                lastError = "'অবশিষ্ট ব্যালেন্স' লেবেলটি পেজে পাওয়া যায়নি। গ্রাহক নম্বরটি ভুল হতে পারে, অথবা সাইটের HTML পরিবর্তন হয়েছে।"
                Log.e(TAG, lastError!!)
                return@withContext null
            }

            val balanceInput = balanceLabel.nextElementSibling()?.select("input")?.first()
            val balanceStr = balanceInput?.attr("value")?.trim()
            if (balanceStr.isNullOrEmpty()) {
                lastError = "ব্যালেন্স ফিল্ড খালি পাওয়া গেছে।"
                Log.e(TAG, lastError!!)
                return@withContext null
            }

            val parsed = balanceStr.toFloatOrNull()
            if (parsed == null) {
                lastError = "ব্যালেন্স মান ('$balanceStr') সংখ্যায় রূপান্তর করা যায়নি।"
                Log.e(TAG, lastError!!)
            }
            parsed
        } catch (e: java.net.SocketTimeoutException) {
            lastError = "সংযোগে সময় বেশি লেগে যাচ্ছে (timeout)। নেটওয়ার্ক ধীর অথবা সাইট রেসপন্স দিচ্ছে না।"
            Log.e(TAG, lastError!!, e)
            null
        } catch (e: IOException) {
            lastError = "নেটওয়ার্ক/সংযোগ এরর: ${e.message}"
            Log.e(TAG, lastError!!, e)
            null
        } catch (e: Exception) {
            lastError = "অপ্রত্যাশিত এরর: ${e.javaClass.simpleName} - ${e.message}"
            Log.e(TAG, lastError!!, e)
            null
        }
    }
}
