package com.jjbaksa.data.model.user

data class UserResp(
    var account: String,
    var email: String,
    var id: Long,
    var nickname: String,
    var oauthType: String,
    var profileImage: UserProfileResp,
    var userCountResp: UserCountResp,
    var userType: String
)
