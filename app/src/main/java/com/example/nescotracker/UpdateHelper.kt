package com.example.nescotracker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object UpdateHelper {

    private const val GITHUB_USER = "Rony-Mia" 
    private const val REPO_NAME = "NescoTracker"
    private const val API_URL = "https://api.github.com/repos/$GITHUB_USER/$REPO_NAME/releases/latest"

    fun checkForUpdates(context: Context) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API_URL)
            .header("Accept", "application/vnd.github.v3+json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    try {
                        val json = JSONObject(jsonString)
                        val tagName = json.getString("tag_name")
                        val assets = json.getJSONArray("assets")
                        if (assets.length() > 0) {
                            val downloadUrl = assets.getJSONObject(0).getString("browser_download_url")
                            
                            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                context.packageManager.getPackageInfo(context.packageName, 0)
                            } else {
                                context.packageManager.getPackageInfo(context.packageName, 0)
                            }
                            val currentVersionName = packageInfo.versionName

                            val normalizedTagName = if (tagName.startsWith("v")) tagName.substring(1) else tagName
                            
                            if (normalizedTagName != currentVersionName) {
                                (context as? MainActivity)?.runOnUiThread {
                                    showUpdateDialog(context, tagName, downloadUrl)
                                }
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
                try {
                    val file = File(context.externalCacheDir, "update.apk")
                    val fos = FileOutputStream(file)
                    fos.write(body.bytes())
                    fos.close()
                    (context as? MainActivity)?.runOnUiThread {
                        installApk(context, file)
                    }
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
