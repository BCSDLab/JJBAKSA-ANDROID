package com.jjbaksa.data.mapper

import com.google.gson.Gson
import com.jjbaksa.domain.BaseResp
import com.jjbaksa.domain.resp.user.LoginResult

object RespMapper {
    fun errorMapper(json: String): BaseResp {
        val startIdx = json.indexOf(",\"errorTrace")
        var newJson = json.substring(0, startIdx) + "}"
        val gson = Gson()
        val baseResp = gson.fromJson(newJson, BaseResp::class.java)
        return baseResp
    }
}