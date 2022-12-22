package com.jjbaksa.jjbaksa.util

import androidx.recyclerview.widget.DiffUtil
import com.jjbaksa.domain.resp.shop.ShopsRespContent

object ShopListDiffUtil : DiffUtil.ItemCallback<ShopsRespContent>() {
    override fun areItemsTheSame(oldItem: ShopsRespContent, newItem: ShopsRespContent): Boolean {
        return oldItem.shopId == newItem.shopId
    }

    override fun areContentsTheSame(oldItem: ShopsRespContent, newItem: ShopsRespContent): Boolean {
        return oldItem == newItem
    }
}
