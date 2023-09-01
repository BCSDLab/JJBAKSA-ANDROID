package com.jjbaksa.domain.resp.scrap

data class GetScraps(
    val content: List<UserScrapsShop> = emptyList()
)
