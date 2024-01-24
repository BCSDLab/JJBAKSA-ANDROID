package com.jjbaksa.jjbaksa.listener

import com.jjbaksa.domain.model.search.Shop

interface OnClickShopListener : AdapterListener {
    fun onClick(item: Shop, position: Int)
}
