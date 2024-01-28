package com.jjbaksa.jjbaksa.ui.follower.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    private val reviewDetailAdapter: ReviewDetailAdapter by lazy {
        ReviewDetailAdapter()
    }
    var isReviewVisible = false
    inner class ViewHolder(private val binding: ItemShopTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: ReviewShopContent) {
            binding.run {
                tvShopTitle.text = shop.name
                tvShopCategory.text = shop.category
                rvShopReviews.adapter = reviewDetailAdapter
                root.setOnClickListener {
                    if (isReviewVisible) {
                        isReviewVisible = false
                        ivShopExpand.setImageResource(R.drawable.sel_jj_check_box_more_info_closed)
                        rvShopReviews.visibility = View.GONE
                    } else {
                        isReviewVisible = true
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
            onListChanged(shop, reviewDetailAdapter)
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