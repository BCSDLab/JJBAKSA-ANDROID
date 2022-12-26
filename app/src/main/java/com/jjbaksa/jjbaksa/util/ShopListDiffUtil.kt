package com.jjbaksa.jjbaksa.util

import androidx.recyclerview.widget.DiffUtil
import com.jjbaksa.domain.resp.shop.ShopsResultContent

object ShopListDiffUtil : DiffUtil.ItemCallback<ShopsResultContent>() {
    override fun areItemsTheSame(oldItem: ShopsResultContent, newItem: ShopsResultContent): Boolean {
        return oldItem.shopId == newItem.shopId
    }

    override fun areContentsTheSame(oldItem: ShopsResultContent, newItem: ShopsResultContent): Boolean {
        return oldItem == newItem
    }
}
