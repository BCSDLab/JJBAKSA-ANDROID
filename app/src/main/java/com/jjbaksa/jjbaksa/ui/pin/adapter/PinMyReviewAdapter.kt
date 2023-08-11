package com.jjbaksa.jjbaksa.ui.pin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.resp.map.ShopMyReviewContent
import com.jjbaksa.jjbaksa.databinding.ItemMyReviewBinding

class PinMyReviewAdapter :
    ListAdapter<ShopMyReviewContent, PinMyReviewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShopMyReviewContent) {
            binding.myReviewContentTextView.text = item.content
            binding.myReviewCreatedDateTextView.text = item.createdAt
            binding.reviewStarCountTextView.text = item.rate.toFloat().toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ShopMyReviewContent>() {
            override fun areItemsTheSame(
                oldItem: ShopMyReviewContent,
                newItem: ShopMyReviewContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ShopMyReviewContent,
                newItem: ShopMyReviewContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
