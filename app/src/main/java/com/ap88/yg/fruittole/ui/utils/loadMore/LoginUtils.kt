package com.ap88.yg.fruittole.ui.utils.loadMore

import com.ap88.yg.fruittole.extensions.DelegatesExt
import com.ap88.yg.fruittole.ui.App

/**
 * Created by duanlei on 2018/3/5.
 */
object LoginUtils {
    var token: String by DelegatesExt.preference(App.instance.applicationContext,
            "token", "")

    fun isLogin(): Boolean {
        return !token.isEmpty()
    }

}