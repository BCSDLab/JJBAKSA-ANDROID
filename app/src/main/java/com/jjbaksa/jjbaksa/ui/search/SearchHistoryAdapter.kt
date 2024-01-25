package com.jjbaksa.jjbaksa.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.jjbaksa.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter(
    private val onClickHistory: (String) -> Unit,
    private val onClickDelete: (String) -> Unit
) : ListAdapter<String, SearchHistoryAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
    }

    inner class ViewHolder(private val binding: ItemSearchHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: String) {
            binding.tvItemSearchHistory.text = item
            binding.clItemSearchHistory.setOnClickListener { onClickHistory(item) }
            binding.ivItemSearchHistoryDelete.setOnClickListener { onClickDelete(item) }
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
