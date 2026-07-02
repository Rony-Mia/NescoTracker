package com.example.nescotracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nescotracker.data.Account
import com.example.nescotracker.databinding.ItemAccountBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AccountUiItem(
    val account: Account,
    val latestBalance: Float?,
    val lastCheckedAt: Long?
)

class AccountAdapter(
    private val onClick: (Account) -> Unit,
    private val onShareClick: (AccountUiItem) -> Unit
) : ListAdapter<AccountUiItem, AccountAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val timeFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.ENGLISH)

        fun bind(item: AccountUiItem) {
            binding.tvNickname.text = item.account.nickname
            binding.tvCustomerNo.text = "মিটার নং: ${item.account.customerNo}"

            if (item.latestBalance != null) {
                binding.tvBalance.text = "৳${"%.2f".format(item.latestBalance)}"
                val isLow = item.latestBalance <= item.account.lowBalanceLimit
                binding.tvBalance.setTextColor(
                    Color.parseColor(if (isLow) "#FF6B6B" else "#FFFFFF")
                )
                binding.tvLowBalanceTag.visibility =
                    if (isLow) android.view.View.VISIBLE else android.view.View.GONE
            } else {
                binding.tvBalance.text = "চেক করা হয়নি"
                binding.tvBalance.setTextColor(Color.parseColor("#A9B8B2"))
                binding.tvLowBalanceTag.visibility = android.view.View.GONE
            }

            binding.tvLastChecked.text = if (item.lastCheckedAt != null) {
                "সর্বশেষ চেক: ${timeFormat.format(Date(item.lastCheckedAt))}"
            } else {
                "এখনো চেক করা হয়নি"
            }

            binding.root.setOnClickListener { onClick(item.account) }
            binding.btnShare.setOnClickListener { onShareClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AccountUiItem>() {
        override fun areItemsTheSame(oldItem: AccountUiItem, newItem: AccountUiItem) =
            oldItem.account.id == newItem.account.id

        override fun areContentsTheSame(oldItem: AccountUiItem, newItem: AccountUiItem) =
            oldItem == newItem
    }
}
