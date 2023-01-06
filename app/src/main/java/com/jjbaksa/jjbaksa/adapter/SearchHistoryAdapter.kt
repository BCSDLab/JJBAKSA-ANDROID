package com.jjbaksa.jjbaksa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.jjbaksa.databinding.ItemSearchHistoryBinding
import com.jjbaksa.jjbaksa.util.StringDiffUtil

class SearchHistoryAdapter :
    ListAdapter<String, SearchHistoryAdapter.ViewHolder>(StringDiffUtil) {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: String,
            onClickListener: OnClickListener,
        ) {
            binding.item = item

            binding.constraintLayoutSearchHistoryItemRoot.setOnClickListener {
                onClickListener.onClick(item)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: String)
    }

    inline fun setOnClickListener(crossinline item: (String) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(position: String) {
                item(position)
            }
        }
    }
}
