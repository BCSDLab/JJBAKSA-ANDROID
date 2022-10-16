package com.jjbaksa.jjbaksa.util

fun String.toSHA256() = RegexUtil.generateSHA256(this)
