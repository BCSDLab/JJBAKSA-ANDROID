package com.jjbaksa.jjbaksa.listener

import com.jjbaksa.domain.resp.search.Shop

interface OnClickShopListener : AdapterListener {
    fun onClick(item: Shop, position: Int)
}
