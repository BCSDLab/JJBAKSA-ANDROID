package com.jjbaksa.jjbaksa.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.Signature
import java.util.Base64

fun String.toSHA256() = RegexUtil.generateSHA256(this)