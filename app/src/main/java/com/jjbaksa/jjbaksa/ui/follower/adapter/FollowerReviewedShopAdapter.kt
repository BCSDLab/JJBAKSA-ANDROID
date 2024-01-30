package com.jjbaksa.jjbaksa.ui.follower.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.review.ReviewShopContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemShopTitleBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter.ReviewDetailAdapter

class FollowerReviewedShopAdapter(
    private val onListChanged: (ReviewShopContent, ReviewDetailAdapter) -> Unit
) : ListAdapter<ReviewShopContent, FollowerReviewedShopAdapter.ViewHolder>(diffUtil) {

    private val reviewDetailAdapter: MutableMap<String, ReviewDetailAdapter> = mutableMapOf()

    inner class ViewHolder(private val binding: ItemShopTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: ReviewShopContent) {
            binding.run {
                tvShopTitle.text = shop.name
                tvShopCategory.text = shop.category
                if(reviewDetailAdapter[shop.placeId] == null)
                    reviewDetailAdapter[shop.placeId] = ReviewDetailAdapter()
                rvShopReviews.adapter = reviewDetailAdapter[shop.placeId]
                root.setOnClickListener {
                    if (rvShopReviews.visibility == View.VISIBLE) {
                        ivShopExpand.setImageResource(R.drawable.sel_jj_check_box_more_info_closed)
                        rvShopReviews.visibility = View.GONE
                    } else {
                        ivShopExpand.setImageResource(R.drawable.sel_jj_check_box_more_info_opened)
                        rvShopReviews.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemShopTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onCurrentListChanged(
        previousList: MutableList<ReviewShopContent>,
        currentList: MutableList<ReviewShopContent>
    ) {
        currentList.forEach { shop ->
            if(reviewDetailAdapter[shop.placeId] == null) reviewDetailAdapter[shop.placeId] = ReviewDetailAdapter()
            reviewDetailAdapter[shop.placeId]?.let {
                onListChanged(shop, it)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ReviewShopContent>() {

            override fun areItemsTheSame(oldItem: ReviewShopContent, newItem: ReviewShopContent): Boolean {
                return oldItem.shopId == newItem.shopId
            }

            override fun areContentsTheSame(oldItem: ReviewShopContent, newItem: ReviewShopContent): Boolean {
                return oldItem == newItem
            }
        }
    }
}
