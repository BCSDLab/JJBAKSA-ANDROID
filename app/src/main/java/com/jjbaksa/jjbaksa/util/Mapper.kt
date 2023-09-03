package com.jjbaksa.jjbaksa.util

import com.jjbaksa.domain.model.shop.ShopsMapsContent
import com.jjbaksa.jjbaksa.model.ShopContent

fun ShopsMapsContent.toShopContent() = ShopContent(
    placeId = placeId, name = name, lat = lat, lng = lng, photo = photo
)
