package com.jjbaksa.jjbaksa.ui.search

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jjbaksa.domain.model.search.Shop
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseViewHolder
import com.jjbaksa.jjbaksa.databinding.ItemLoadingBinding
import com.jjbaksa.jjbaksa.databinding.ItemSearchShopBinding
import com.jjbaksa.jjbaksa.listener.AdapterListener
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchShopAdapter(
    private val context: Context,
    private val adapterListener: AdapterListener
) : RecyclerView.Adapter<BaseViewHolder<Shop>>() {
    private val shopList = mutableListOf<Shop>()

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private val decimalFormat = DecimalFormat("#.##")
        .apply { roundingMode = RoundingMode.UP }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Shop> {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                ItemSearchShopBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_TYPE_LOADING -> LoadingViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent, false
                )
            )

            else -> ViewHolder(
                ItemSearchShopBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Shop>, position: Int) {
        val item = shopList[position]
        holder.bindViews(item, position, adapterListener)
    }

    override fun getItemViewType(position: Int): Int {
        return shopList[position].type
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    fun addAll(data: MutableList<Shop>) {
        data.forEach { add(it) }
    }

    fun add(data: Shop) {
        shopList.add(data)
        notifyItemInserted(shopList.size - 1)
    }
    fun addLoading() {
        if (shopList.filter { it.type == VIEW_TYPE_LOADING }.count() == 0) {
            add(Shop(type = VIEW_TYPE_LOADING))
        }
    }

    fun removeLoading() {
        if (!shopList.isEmpty()) {
            var position = shopList.size - 1
            var item = shopList[position]
            shopList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        shopList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSearchShopBinding) :
        BaseViewHolder<Shop>(binding) {
        override fun bindViews(item: Shop, position: Int, adapterListener: AdapterListener) {
            with(binding) {
                tvItemSearchShopTitle.text = item.name
                if (item.photoToken.isEmpty()) {
                    llItemSearchShopPhotoError.visibility = View.VISIBLE
                    ivItemSearchShopPhoto.visibility = View.GONE
                } else {
                    Glide.with(context)
                        .load(item.photoToken)
                        .placeholder(R.drawable.ic_error_img)
                        .error(R.drawable.ic_error_img)
                        .override(
                            binding.ivItemSearchShopPhoto.width,
                            binding.ivItemSearchShopPhoto.height
                        )
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.ivItemSearchShopPhoto)
                    llItemSearchShopPhotoError.visibility = View.GONE
                    ivItemSearchShopPhoto.visibility = View.VISIBLE
                }
                tvItemSearchShopCategory.text = item.category
                // tvItemSearchShopAddress.text = "${item.formattedAddress.trim()} | ${decimalFormat.format(item.dist / 1000.0)}km"
                var addressString = "${item.formattedAddress.trim()} | ${decimalFormat.format(item.dist / 1000.0)}km"
                var sp = SpannableString("${item.formattedAddress} | ${decimalFormat.format(item.dist / 1000.0)}km")
                sp.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_666666)), item.formattedAddress.length, addressString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                tvItemSearchShopAddress.setText(sp)
                tvItemSearchShopRating.text = item.totalRating.toString()
                if (item.openNow) {
                    tvItemSearchShopOpen.text = context.getString(R.string.open_now)
                } else {
                    tvItemSearchShopOpen.text = context.getString(R.string.open_not_now)
                }
                if (item.totalRating == 0) {
                    tvItemSearchShopReviewCnt.text = context.getString(R.string.empty_review)
                } else {
                    tvItemSearchShopReviewCnt.text = String.format(context.getString(R.string.format_review), item.ratingCount)
                }
            }
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) : BaseViewHolder<Shop>(binding) {
        override fun bindViews(item: Shop, position: Int, adapterListener: AdapterListener) {
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Shop>() {
            override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
                return oldItem == newItem
            }
        }
    }
}
