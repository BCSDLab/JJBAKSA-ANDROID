package com.jjbaksa.data.entity

import androidx.room.Entity
import javax.annotation.concurrent.Immutable

@Immutable
@Entity(primaryKeys = [("id")])
data class UserEntity(
    var id: Int
)
