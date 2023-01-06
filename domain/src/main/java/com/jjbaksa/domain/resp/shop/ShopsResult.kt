package com.jjbaksa.domain.resp.shop

data class ShopsResult(
    val content: List<ShopsResultContent>,
    val pageable: ShopsResultPageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: ShopsResultSort,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)

data class ShopsResultContent(
    val shopId: Int,
    val placeId: String,
    val placeName: String,
    val address: String,
    val x: String,
    val y: String,
    val dist: Double,
    val score: Double
)

data class ShopsResultPageable(
    val sort: ShopsResultSort,
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class ShopsResultSort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)
