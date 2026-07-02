package com.example.nescotracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nescotracker.data.RechargeEvent
import com.example.nescotracker.databinding.ItemRechargeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RechargeAdapter : ListAdapter<RechargeEvent, RechargeAdapter.ViewHolder>(DiffCallback()) {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRechargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRechargeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RechargeEvent) {
            binding.tvRechargeAmount.text = "+ ৳${"%.2f".format(item.amount)}"
            binding.tvRechargeDate.text = dateFormat.format(Date(item.date))
            binding.tvRechargeBalanceAfter.text = "ব্যালেন্স: ৳${"%.2f".format(item.balanceAfter)}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RechargeEvent>() {
        override fun areItemsTheSame(oldItem: RechargeEvent, newItem: RechargeEvent) =
            oldItem.date == newItem.date
        override fun areContentsTheSame(oldItem: RechargeEvent, newItem: RechargeEvent) =
            oldItem == newItem
    }
}
