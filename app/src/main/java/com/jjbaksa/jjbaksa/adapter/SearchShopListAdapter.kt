package com.jjbaksa.jjbaksa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jjbaksa.domain.resp.shop.ShopsResultContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemSearchShopListBinding
import com.jjbaksa.jjbaksa.util.ShopListDiffUtil

class SearchShopListAdapter :
    ListAdapter<ShopsResultContent, SearchShopListAdapter.ViewHolder>(ShopListDiffUtil) {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchShopListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemSearchShopListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ShopsResultContent,
            onClickListener: OnClickListener,
        ) {
            binding.item = item
            // TODO Update image url after API provides it
            binding.imageViewSearchRestaurantImage.load(R.mipmap.ic_shop_item_placeholder) {
                placeholder(R.mipmap.ic_shop_item_placeholder)
            }

            binding.imageButtonSearchRestaurantMap.setOnClickListener {
                onClickListener.onClick(item)
            }
        }
    }

    interface OnClickListener {
        fun onClick(item: ShopsResultContent)
    }

    inline fun setOnClickListener(crossinline item: (ShopsResultContent) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(item: ShopsResultContent) {
                item(item)
            }
        }
    }
}
