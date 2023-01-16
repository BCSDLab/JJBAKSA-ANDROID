package com.jjbaksa.data.mapper

object EmailRespMapper {
    fun mapToBoolean(resultCode: Int): Boolean = when (resultCode) {
        200 -> true
        else -> false
    }
}
