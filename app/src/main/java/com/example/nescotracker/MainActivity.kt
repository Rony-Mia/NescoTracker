package com.example.nescotracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.nescotracker.data.Repository
import com.example.nescotracker.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository
    private lateinit var adapter: AccountAdapter

    // ফুল লিস্ট এখানে রাখা হয়, সার্চ করলে এখান থেকে ফিল্টার করে adapter-কে দেওয়া হয়
    private var fullList: List<AccountUiItem> = emptyList()
    private var currentQuery: String = ""

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    private val addAccountLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { checkAllBalancesNow() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        repository = Repository(this)
        askNotificationPermission()
        setupRecyclerView()
        observeAccounts()
        setupPeriodicWork()
        
        // অ্যাপ চালু হওয়ার সময় আপডেট চেক করবে
        UpdateHelper.checkForUpdates(this)

        binding.fabAddAccount.setOnClickListener {
            addAccountLauncher.launch(Intent(this, AddAccountActivity::class.java))
        }

        binding.swipeRefresh.setOnRefreshListener { checkAllBalancesNow() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "নাম বা মিটার নম্বর দিয়ে খুঁজুন..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true
            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText.orEmpty()
                applyFilter()
                return true
            }
        })

        updateDarkModeIcon(menu.findItem(R.id.action_dark_mode))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_dark_mode) {
            ThemePrefs.toggle(this)
            recreate()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDarkModeIcon(item: MenuItem) {
        val isDark = ThemePrefs.isDarkActive(this)
        item.setIcon(if (isDark) R.drawable.ic_dark_mode else R.drawable.ic_dark_mode)
        item.title = if (isDark) "লাইট মোড" else "ডার্ক মোড"
    }

    private fun setupRecyclerView() {
        adapter = AccountAdapter(
            onClick = { account ->
                val intent = Intent(this, AccountDetailActivity::class.java)
                intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_ID, account.id)
                startActivity(intent)
            },
            onShareClick = { item ->
                ShareHelper.shareBalance(this, item.account, item.latestBalance, item.lastCheckedAt)
            }
        )
        binding.rvAccounts.layoutManager = LinearLayoutManager(this)
        binding.rvAccounts.adapter = adapter
    }

    private fun observeAccounts() {
        lifecycleScope.launch {
            combine(
                repository.allAccountsFlow(),
                repository.latestBalancesFlow()
            ) { accounts, latestRecords ->
                val latestByAccount = latestRecords.associateBy { it.accountId }
                accounts.map { account ->
                    val record = latestByAccount[account.id]
                    AccountUiItem(account, record?.balance, record?.checkedAt)
                }
            }.collect { items ->
                fullList = items
                applyFilter()
                WidgetUpdater.refreshAll(this@MainActivity)
            }
        }
    }

    private fun applyFilter() {
        val query = currentQuery.trim().lowercase(Locale.getDefault())
        val filtered = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.account.nickname.lowercase(Locale.getDefault()).contains(query) ||
                    it.account.customerNo.contains(query)
            }
        }
        adapter.submitList(filtered)

        val isEmpty = filtered.isEmpty()
        binding.emptyState.visibility = if (isEmpty) android.view.View.VISIBLE else android.view.View.GONE
        binding.tvEmptyTitle.text = if (query.isNotEmpty()) "কোনো ফলাফল পাওয়া যায়নি" else "কোনো মিটার যোগ করা নেই"
        binding.tvEmptySubtitle.text = if (query.isNotEmpty())
            "\"$currentQuery\" দিয়ে কিছু খুঁজে পাওয়া যায়নি"
        else
            "নিচের + বাটনে চাপ দিয়ে নতুন মিটার যোগ করুন"
    }

    private fun checkAllBalancesNow() {
        binding.swipeRefresh.isRefreshing = true
        lifecycleScope.launch {
            val accounts = repository.getActiveAccounts()
            for (account in accounts) {
                val balance = repository.checkAndSaveBalance(account)
                
                // ম্যানুয়াল রিফ্রেশেও নোটিফিকেশন টেস্ট করার জন্য এই অংশটি যোগ করা হলো
                if (balance != null) {
                    if (balance <= account.lowBalanceLimit) {
                        val estimate = repository.calculateUsageEstimate(account.id, balance)
                        NotificationHelper.showLowBalanceAlert(
                            this@MainActivity, account, balance, estimate.estimatedDaysLeft
                        )
                    } else {
                        NotificationHelper.cancelLowBalanceAlert(this@MainActivity, account.id)
                    }
                }
            }
            binding.swipeRefresh.isRefreshing = false
            WidgetUpdater.refreshAll(this@MainActivity)
            if (accounts.isEmpty()) {
                Toast.makeText(
                    this@MainActivity, "কোনো মিটার যোগ করা নেই — + বাটনে চাপুন", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /** প্রতি ৩ ঘণ্টা পরপর ব্যাকগ্রাউন্ডে সব অ্যাকাউন্টের ব্যালেন্স চেক হবে */
    private fun setupPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<BalanceWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            BalanceWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // অ্যাক্টিভিটি খুললেই লিস্ট রিফ্রেশ হয়ে যাবে (Flow দিয়ে অটো-আপডেট হচ্ছে)
    }
}
