package com.jjbaksa.jjbaksa.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.jjbaksa.databinding.ItemTrendBinding

class TrendTextAdapter(private val onClickTrendText: (String) -> Unit) : ListAdapter<String, TrendTextAdapter.ViewHolder>(diffUtil){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTrendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
    }

    inner class ViewHolder(private val binding: ItemTrendBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(item: String) {
            binding.tvTrend.text = item
            binding.clItemTrend.setOnClickListener { onClickTrendText(item) }
        }

    }
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}