package com.jjbaksa.jjbaksa.ui.pin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.resp.follower.FollowerShopReviewContent
import com.jjbaksa.jjbaksa.databinding.ItemFriendReviewBinding

class PinFriendReviewAdapter(
    private val onReport: (FollowerShopReviewContent) -> Unit
) :
    ListAdapter<FollowerShopReviewContent, PinFriendReviewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemFriendReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowerShopReviewContent) {
            binding.friendReviewNameTextView.text = item.userReviewResponse.nickname
            binding.friendReviewAccountTextView.text = item.userReviewResponse.account
            binding.friendReviewContentTextView.text = item.content
            binding.friendReviewCreatedDateTextView.text = item.createdAt
            binding.reviewStarCountTextView.text = item.rate.toFloat().toString()

            binding.myFriendReviewReportTextView.setOnClickListener {
                onReport(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFriendReviewBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<FollowerShopReviewContent>() {
            override fun areItemsTheSame(
                oldItem: FollowerShopReviewContent,
                newItem: FollowerShopReviewContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowerShopReviewContent,
                newItem: FollowerShopReviewContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}