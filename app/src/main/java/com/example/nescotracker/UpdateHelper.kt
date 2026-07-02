package com.example.nescotracker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.example.nescotracker.BuildConfig
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object UpdateHelper {

    // TODO: এখানে আপনার GitHub ইউজারনেম এবং রিপোজিটরি নাম বসান
    private const val GITHUB_USER = "YourUsername" 
    private const val REPO_NAME = "NescoTracker"
    private const val API_URL = "https://api.github.com/repos/$GITHUB_USER/$REPO_NAME/releases/latest"

    fun checkForUpdates(context: Context) {
        val client = OkHttpClient()
        val request = Request.Builder().url(API_URL).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // নেটওয়ার্ক এরর হলে কিছু করার দরকার নেই
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    try {
                        val json = JSONObject(jsonString)
                        val serverVersionCode = json.optInt("id", 0) // GitHub Release ID কে আমরা ভার্সন হিসেবে ধরতে পারি, অথবা ট্যাগ নেম
                        val tagName = json.getString("tag_name")
                        val downloadUrl = json.getJSONArray("assets")
                            .getJSONObject(0).getString("browser_download_url")

                        // অ্যাপের বর্তমান ভার্সন কোডের সাথে তুলনা (BuildConfig.VERSION_CODE)
                        // আপনি যদি GitHub Tag ব্যবহার করেন (যেমন v2.1), তবে সেভাবে লজিক লিখতে হবে।
                        // এখানে সহজ করার জন্য আমরা ধরি যদি ট্যাগের নাম বর্তমান ভার্সন নেমের সাথে না মিলে তবেই আপডেট।
                        val currentVersionName = context.packageManager
                            .getPackageInfo(context.packageName, 0).versionName

                        if (tagName != currentVersionName) {
                            (context as? MainActivity)?.runOnUiThread {
                                showUpdateDialog(context, tagName, downloadUrl)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    private fun showUpdateDialog(context: Context, newVersion: String, downloadUrl: String) {
        AlertDialog.Builder(context)
            .setTitle("নতুন আপডেট পাওয়া গেছে!")
            .setMessage("ভার্সন $newVersion এখন ডাউনলোডের জন্য উপলব্ধ। আপনি কি এখন আপডেট করতে চান?")
            .setPositiveButton("আপডেট") { _, _ ->
                downloadAndInstallApk(context, downloadUrl)
            }
            .setNegativeButton("পরে", null)
            .show()
    }

    private fun downloadAndInstallApk(context: Context, url: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                val body = response.body ?: return
                val file = File(context.externalCacheDir, "update.apk")
                
                try {
                    val fos = FileOutputStream(file)
                    fos.write(body.bytes())
                    fos.close()
                    installApk(context, file)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun installApk(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
