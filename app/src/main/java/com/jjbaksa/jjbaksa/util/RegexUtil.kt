package com.jjbaksa.jjbaksa.util

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{2,16}\$"

@Module
@InstallIn(SingletonComponent::class)
object RegexUtil {
    fun String.isPasswordRuleMatch(): Boolean {
        val regex = Regex(PASSWORD_REGEX)
        return regex.containsMatchIn(this)
    }
}
