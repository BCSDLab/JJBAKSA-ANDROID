package com.jjbaksa.data.model.shop

import com.jjbaksa.domain.BaseResp

data class TrendingResp(
    val trendings: List<String>
) : BaseResp()
