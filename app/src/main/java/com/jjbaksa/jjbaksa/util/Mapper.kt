package com.jjbaksa.jjbaksa.util

import com.jjbaksa.domain.resp.map.MapShopContent
import com.jjbaksa.jjbaksa.model.ShopContent

fun MapShopContent.toShopContent() = ShopContent(
    placeId = placeId, name = name, lat = lat, lng = lng, photo = photo
)
