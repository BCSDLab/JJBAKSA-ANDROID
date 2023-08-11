package com.jjbaksa.jjbaksa.util.mock

import android.content.Context
import com.google.gson.Gson
import com.jjbaksa.domain.resp.map.MapShopContent
import ted.gun0912.clustering.TedClustering
import java.io.IOException

fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = this.resources.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        val gson = Gson()
        gson.fromJson(String(buffer), classT)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
