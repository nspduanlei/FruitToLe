package com.ap88.yg.fruittole.ui.utils

import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.extensions.DelegatesExt
import com.ap88.yg.fruittole.ui.App
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl

/**
 * Created by duanlei on 2018/3/5.
 */
object LoginUtils {
    var token: String by DelegatesExt.preference(App.instance.applicationContext,
            "token", "")

    fun isLogin(): Boolean {
        return !token.isEmpty()
    }

    fun checkLogin(delegate: BaseDelegate): Boolean {
        return if (token.isEmpty()) {
            gotoLogin(delegate)
            false
        } else {
            true
        }
    }

    public fun gotoLogin(delegate: BaseDelegate) {
        delegate.start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/login"))
    }

    public fun popToLogin(delegate: BaseDelegate) {
        delegate.startWithPop(WebDelegateImpl.create(ApiStores.URL_WEB + "#/login"))
    }
}