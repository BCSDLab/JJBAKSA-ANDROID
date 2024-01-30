package com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.review.MyReviewShopsContent
import com.jjbaksa.jjbaksa.databinding.ItemMyReviewBinding

class ReviewDetailAdapter :
    ListAdapter<MyReviewShopsContent, ReviewDetailAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyReviewShopsContent) {
            binding.myReviewContentTextView.text = item.content
            binding.myReviewCreatedDateTextView.text = item.createdAt
            binding.tvReviewStarCount.text = item.rate.toFloat().toString()
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
        val diffUtil = object : DiffUtil.ItemCallback<MyReviewShopsContent>() {
            override fun areItemsTheSame(
                oldItem: MyReviewShopsContent,
                newItem: MyReviewShopsContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MyReviewShopsContent,
                newItem: MyReviewShopsContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
