package com.jjbaksa.jjbaksa.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat

fun String.toSHA256() = RegexUtil.generateSHA256(this)
fun Context.hasPermission(permission: Array<String>): Boolean =
    permission.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
fun Context.hasPermission(permission: String): Boolean =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
fun Context.checkPermissionsAndRequest(
    permission: Array<String>,
    request: ActivityResultLauncher<Array<String>>
): Boolean {
    val result = hasPermission(permission)
    if (!result) {
        request.launch(permission)
    }
    return result
}

fun Context.setTextProperties(text: String, range: Int): SpannableStringBuilder {
    val builder = SpannableStringBuilder(text)
    val boldSpan = StyleSpan(Typeface.BOLD)
    builder.setSpan(boldSpan, 0, range, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    return builder
}
