package com.jjbaksa.data.model.findid

data class FindIdResp(
    val account: String,
    val email: String,
    val id: Int,
    val nickname: String,
    val oauthType: String,
    val profileImage: Map<String, Any>,
    val userCountResponse: Map<String, Int>,
    val userType: String
)
