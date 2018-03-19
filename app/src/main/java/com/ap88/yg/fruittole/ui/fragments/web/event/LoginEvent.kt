package com.ap88.yg.fruittole.ui.fragments.web.event

import com.alibaba.fastjson.JSON
import com.ap88.yg.fruittole.ui.utils.LoginUtils

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class LoginEvent : Event() {

    override fun execute(params: String): String? {
        //Toast.makeText(context, params, Toast.LENGTH_LONG).show()
        //Log.e("test0001", params)

        val token = JSON.parseObject(params).getJSONObject("data").getString("token")
        LoginUtils.token = token


        return null
    }
}
