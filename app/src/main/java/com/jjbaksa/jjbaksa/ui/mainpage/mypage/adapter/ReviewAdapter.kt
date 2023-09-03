package com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.review.ReviewShopContent
import com.jjbaksa.jjbaksa.databinding.ItemMyPageReviewBinding

class ReviewAdapter : ListAdapter<ReviewShopContent, ReviewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMyPageReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewShopContent) {
            binding.titleTextView.text = item.name
            binding.categoryTextView.text = item.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyPageReviewBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<ReviewShopContent>() {
            override fun areItemsTheSame(
                oldItem: ReviewShopContent,
                newItem: ReviewShopContent
            ): Boolean {
                return oldItem.shopId == newItem.shopId
            }

            override fun areContentsTheSame(
                oldItem: ReviewShopContent,
                newItem: ReviewShopContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
