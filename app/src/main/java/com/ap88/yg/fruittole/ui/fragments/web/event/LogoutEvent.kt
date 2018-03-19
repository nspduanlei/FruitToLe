package com.ap88.yg.fruittole.ui.fragments.web.event

import android.util.Log
import android.widget.Toast
import com.ap88.yg.fruittole.ui.utils.LoginUtils

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class LogoutEvent : Event() {

    override fun execute(params: String): String? {
        Toast.makeText(context, params, Toast.LENGTH_LONG).show()
        Log.e("test0001", params)

        val token = ""
        LoginUtils.token = token

        return null
    }
}
