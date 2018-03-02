package com.ap88.yg.fruittole.utils

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by duanlei on 2018/2/26.
 */
object RequestUtils {

    private val mGson: Gson = Gson()

    fun <T> getRequestBody(t: T): RequestBody {
        return RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                mGson.toJson(t))
    }


    fun <T> getRequestBodyJson(t: T): RequestBody {
        return RequestBody.create(
                MediaType.parse("application/json"),
                mGson.toJson(t))
    }

}