package com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.domain.model.scrap.ScrapsContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemMypageScrapShopBinding
import kotlin.math.round

class BookmarkAdapter(
    private val context: Context
) : ListAdapter<ScrapsContent, BookmarkAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMypageScrapShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScrapsContent) {
            binding.titleTextView.text = item.name
            binding.categoryTextView.text = item.category
            binding.starTextView.text =
                (round(item.totalRating.div(item.ratingCount.toDouble()) * 10) / 10).toString()
            if (item.photo.isNullOrEmpty()) {
                binding.shopImageView.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(binding.shopImageView)
                    .load(R.drawable.ic_error_img)
                    .into(binding.shopImageView)
                binding.shopEmptyTextView.text = context.getString(R.string.no_img)
            } else {
                binding.shopImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(binding.shopImageView)
                    .load(item.photo)
                    .placeholder(R.drawable.ic_empty_img)
                    .into(binding.shopImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMypageScrapShopBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<ScrapsContent>() {
            override fun areItemsTheSame(
                oldItem: ScrapsContent,
                newItem: ScrapsContent
            ): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(
                oldItem: ScrapsContent,
                newItem: ScrapsContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
