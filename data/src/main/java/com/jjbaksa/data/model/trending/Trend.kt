package com.jjbaksa.data.model.trending

import com.jjbaksa.domain.BaseResp

data class Trend(
    val trendings: List<String>
) : BaseResp()
