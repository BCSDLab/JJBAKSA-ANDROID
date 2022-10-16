package com.jjbaksa.data.model.user

import com.jjbaksa.domain.BaseResp

data class LoginResp(
    val accessToken: String,
    val refreshToken: String
): BaseResp()
