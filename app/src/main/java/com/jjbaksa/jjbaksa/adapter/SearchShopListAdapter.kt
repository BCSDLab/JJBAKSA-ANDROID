package com.jjbaksa.jjbaksa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.resp.shop.ShopsRespContent
import com.jjbaksa.jjbaksa.databinding.ItemSearchShopListBinding
import com.jjbaksa.jjbaksa.util.ShopListDiffUtil

class SearchShopListAdapter :
    ListAdapter<ShopsRespContent, SearchShopListAdapter.ViewHolder>(ShopListDiffUtil) {

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
            item: ShopsRespContent,
            onClickListener: OnClickListener,
        ) {
            binding.item = item

            binding.imageButtonSearchRestaurantMap.setOnClickListener {
                onClickListener.onClick(item)
            }
        }
    }

    interface OnClickListener {
        fun onClick(item: ShopsRespContent)
    }

    inline fun setOnClickListener(crossinline item: (ShopsRespContent) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(item: ShopsRespContent) {
                item(item)
            }
        }
    }
}