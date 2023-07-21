package com.jjbaksa.jjbaksa.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.jjbaksa.databinding.ItemKeywordBinding

class AutoCompleteKeywordAdapter(
    private val onClickKeyword: (String) -> Unit
) : ListAdapter<String, AutoCompleteKeywordAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
    }

    inner class ViewHolder(private val binding: ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: String) {
            binding.tvItemKeyword.text = item
            binding.clItemKeyword.setOnClickListener { onClickKeyword(item) }
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
