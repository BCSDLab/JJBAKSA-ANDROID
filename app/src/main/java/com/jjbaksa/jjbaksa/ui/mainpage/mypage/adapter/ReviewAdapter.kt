package com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.domain.model.review.ReviewShopContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemMyPageReviewBinding

class ReviewAdapter : ListAdapter<ReviewShopContent, ReviewAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMyPageReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewShopContent) {
            binding.shopTitleTextView.text = item.name
            binding.shopCategoryTextView.text = item.category
            if (item.photos.isEmpty()) {
                binding.shopImageView.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(binding.shopImageView)
                    .load(R.drawable.ic_empty_img)
                    .into(binding.shopImageView)
            } else {
                binding.shopImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(binding.shopImageView)
                    .load(item.photos[0])
                    .into(binding.shopImageView)
            }
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
