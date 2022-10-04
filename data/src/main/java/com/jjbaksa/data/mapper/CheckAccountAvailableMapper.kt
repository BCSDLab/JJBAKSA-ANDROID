package com.jjbaksa.data.mapper

object CheckAccountAvailableMapper {
    fun mapToBoolean(result: String): Boolean = when (result) {
        "OK" -> true
        else -> false
    }
}