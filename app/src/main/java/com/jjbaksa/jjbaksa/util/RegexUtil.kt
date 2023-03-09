package com.jjbaksa.jjbaksa.util

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+=|;:'\"><,.?/-])[A-Za-z\\d~!@#\$%^&*()_+=|;:'\"><,.?\\/-]{8,16}$"
const val EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+.[a-zA-Z]{2,3}$"

@Module
@InstallIn(SingletonComponent::class)
object RegexUtil {
    fun String.isPasswordRuleMatch(): Boolean {
        val regex = Regex(PASSWORD_REGEX)
        return regex.containsMatchIn(this)
    }
    private fun hashString(message: String, algorithm: String): String {
        try {
            val digest = MessageDigest.getInstance(algorithm)
            digest.update(message.toByteArray())
            val hashedBytes = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (hashedByte in hashedBytes) {
                val h = StringBuilder(Integer.toHexString(0xFF and hashedByte.toInt()))
                while (h.length < 2) h.insert(0, "0")
                hexString.append(h)
            }
            return hexString.toString()
        } catch (ex: NoSuchAlgorithmException) {
            ex.printStackTrace()
        }
        return ""
    }
    fun generateSHA256(message: String): String {
        return RegexUtil.hashString(message, "SHA-256")
    }
    fun checkEmailFormat(userEmail: String): Boolean = Pattern.matches(EMAIL_REGEX, userEmail)
}
