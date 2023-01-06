package com.jjbaksa.data.model.shop

import com.jjbaksa.domain.BaseResp

data class ShopsResp(
    val content: List<ShopsRespContent>,
    val pageable: ShopsRespPageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: ShopsRespSort,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
) : BaseResp()

data class ShopsRespContent(
    val shopId: Int,
    val placeId: String,
    val placeName: String,
    val address: String,
    val x: String,
    val y: String,
    val dist: Double,
    val score: Double
)

data class ShopsRespPageable(
    val sort: ShopsRespSort,
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class ShopsRespSort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)
