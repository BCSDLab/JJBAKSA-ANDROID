package com.jjbaksa.data.mapper.scrap

import com.jjbaksa.data.model.scrap.AddShopScrapResp
import com.jjbaksa.data.model.scrap.ScrapsContentResp
import com.jjbaksa.data.model.scrap.ScrapsResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import com.jjbaksa.domain.model.scrap.AddShopScrap
import com.jjbaksa.domain.model.scrap.Scraps
import com.jjbaksa.domain.model.scrap.ScrapsContent
import com.jjbaksa.domain.model.scrap.ShopScrap

fun ShopScrapResp.toShopScrap() = ShopScrap(
    scrapId = scrapId ?: 0,
    placeId = placeId ?: "",
    category = category ?: "",
    name = name ?: "",
    totalRating = totalRating ?: 0,
    ratingCount = ratingCount ?: 0,
    address = address ?: "",
    photo = photo ?: emptyList(),
)

fun AddShopScrapResp.toAddShopScrap() = AddShopScrap(
    id = id ?: 0,
    createdAt = createdAt ?: 0,
    updatedAt = updatedAt ?: 0,
    directory = directory ?: 0,
    shopId = shopId ?: 0
)

fun ScrapsResp.toScraps() = Scraps(
    content = content?.map { it.toScrapsContent() }.orEmpty()
)

fun ScrapsContentResp.toScrapsContent() = ScrapsContent(
    placeId = placeId ?: "",
    name = name ?: "",
    photo = photo ?: "",
    category = category ?: "",
    totalRating = totalRating ?: 0,
    ratingCount = ratingCount ?: 0,
    address = address ?: "",
    scrapId = scrapId ?: 0,
    createdAt = createdAt ?: 0,
    updatedAt = updatedAt ?: 0,
    directory = directory ?: 0
)
