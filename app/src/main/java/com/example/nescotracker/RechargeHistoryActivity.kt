package com.example.nescotracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nescotracker.data.Repository
import com.example.nescotracker.databinding.ActivityRechargeHistoryBinding
import kotlinx.coroutines.launch

class RechargeHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRechargeHistoryBinding
    private lateinit var repository: Repository
    private lateinit var adapter: RechargeAdapter
    private var accountId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRechargeHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        accountId = intent.getLongExtra(EXTRA_ACCOUNT_ID, -1)
        val nickname = intent.getStringExtra(EXTRA_NICKNAME) ?: "মিটার"
        binding.toolbar.title = "🧾 $nickname — রিচার্জ হিস্ট্রি"
        binding.toolbar.setNavigationOnClickListener { finish() }

        repository = Repository(this)
        adapter = RechargeAdapter()
        binding.rvRecharges.layoutManager = LinearLayoutManager(this)
        binding.rvRecharges.adapter = adapter

        if (accountId == -1L) {
            finish()
            return
        }

        observeHistory()
    }

    private fun observeHistory() {
        lifecycleScope.launch {
            repository.rechargeHistoryFlow(accountId).collect { events ->
                adapter.submitList(events)
                binding.emptyState.visibility =
                    if (events.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            }
        }
    }

    companion object {
        const val EXTRA_ACCOUNT_ID = "extra_account_id"
        const val EXTRA_NICKNAME = "extra_nickname"
    }
}
