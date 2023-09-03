package com.jjbaksa.jjbaksa.ui.pin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.jjbaksa.domain.model.review.FollowerReviewShopsContent
import com.jjbaksa.jjbaksa.databinding.ItemFriendReviewBinding

class PinFriendReviewAdapter(
    private val onReport: (FollowerReviewShopsContent) -> Unit
) :
    ListAdapter<FollowerReviewShopsContent, PinFriendReviewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemFriendReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowerReviewShopsContent) {
            binding.friendReviewNameTextView.text = item.userReviewResponse.nickname
            binding.friendReviewAccountTextView.text = item.userReviewResponse.account
            binding.friendReviewContentTextView.text = item.content
            binding.friendReviewCreatedDateTextView.text = item.createdAt
            binding.reviewStarCountTextView.text = item.rate.toFloat().toString()
            Glide.with(binding.friendReviewProfileImageView)
                .load(item.userReviewResponse.profileImage.url)
                .override(40)
                .transform(CircleCrop())
                .into(binding.friendReviewProfileImageView)

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
        val diffUtil = object : DiffUtil.ItemCallback<FollowerReviewShopsContent>() {
            override fun areItemsTheSame(
                oldItem: FollowerReviewShopsContent,
                newItem: FollowerReviewShopsContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowerReviewShopsContent,
                newItem: FollowerReviewShopsContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
