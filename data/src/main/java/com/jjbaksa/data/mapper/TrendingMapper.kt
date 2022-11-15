package com.jjbaksa.data.mapper

import com.jjbaksa.data.model.shop.TrendingResp
import com.jjbaksa.domain.resp.shop.TrendingResult

object TrendingMapper {
    fun TrendingResp.mapToTrendingResult(): TrendingResult = TrendingResult(this.trendings)
}