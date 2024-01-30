package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.user.User

data class Follow(
    @SerializedName("follower")
    val follower: User? = User()

)
