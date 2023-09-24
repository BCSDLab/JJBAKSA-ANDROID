package com.jjbaksa.domain.model.shop

data class Period(
    val close: Close = Close(),
    val open: Open = Open()
)