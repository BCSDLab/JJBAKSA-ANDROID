package com.jjbaksa.data.mapper

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import okio.source
import java.io.File
import java.net.URLConnection
import kotlin.math.abs

object FormDataUtil {
    val secUnit = 60L
    val milliSec = 1000L

    fun getEmptyBody(): MultipartBody.Part {
        return MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("", "")
            .build().part(0)
    }

    fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }

    fun getImageBody(key: String, uri: Uri): MultipartBody.Part {
        try {
            return getImageBody(key, File(uri.path))
        } catch (e: Exception) {
            Log.e("jdm_Tag", e.message.toString())
            throw e
        }
    }

    fun getImageBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody(
                URLConnection.guessContentTypeFromName(file.name).toMediaType()
            )
        )
    }

    fun getVideoBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("video/*".toMediaType())
        )
    }

    @SuppressLint("Range")
    fun Uri.asMultipart(
        key: String = "multipartFile",
        contentResolver: ContentResolver
    ): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null)?.let {
            it.use { cursor ->
                if (cursor.moveToNext()) {
                    val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    val requestBody = object : RequestBody() {
                        override fun contentType(): MediaType? {
                            return contentResolver.getType(this@asMultipart)?.toMediaType()
                        }

                        override fun writeTo(sink: BufferedSink) {
                            sink.writeAll(
                                contentResolver.openInputStream(this@asMultipart)?.source()!!
                            )
                        }
                    }
                    MultipartBody.Part.createFormData(key, displayName, requestBody)
                } else {
                    null
                }
            }
        }
    }

    fun secondToStringHHMMWithUnit(timeS: Long, hourText: String, minuteText: String): String {
        val timeArr = stringArrForTimeHHMMSS(timeS, false)
        val bNegative = timeS < 0
        val sb = StringBuilder()
        if (bNegative) {
            sb.append("-")
        }

        if (timeArr[0] > 0) {
            sb.append(String.format("%d$hourText ", timeArr[0]))
        }
        sb.append(String.format("%d$minuteText", timeArr[1]))
        return sb.toString()
    }

    fun stringArrForTimeHHMMSS(time: Long, isMillis: Boolean): List<Long> {
        val absTimeMs = abs(time)
        val totalSeconds = if (isMillis) {
            absTimeMs / milliSec
        } else {
            absTimeMs
        }
        val seconds = totalSeconds % secUnit
        val minutes = totalSeconds / secUnit % secUnit
        val hours = totalSeconds / (secUnit * secUnit)
        return listOf(hours, minutes, seconds)
    }
}
