package com.jjbaksa.data.mapper

import com.jjbaksa.data.model.shop.TrendingResp
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.shop.TrendingResult

object TrendingMapper {
    fun TrendingResp.mapTrendingToResult(): RespResult<TrendingResult> {
        return if (this.code == 0) {
            RespResult.Success(TrendingResult(this.trendings))
        } else {
            RespResult.Error(ErrorType(this.errorMessage, this.code))
        }
    }
}
