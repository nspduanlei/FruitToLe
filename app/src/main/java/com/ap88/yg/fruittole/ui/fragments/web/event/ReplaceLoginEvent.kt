package com.ap88.yg.fruittole.ui.fragments.web.event

import com.ap88.yg.fruittole.ui.utils.LoginUtils
import com.ap88.yg.fruittole.utils.T

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class ReplaceLoginEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)
        LoginUtils.popToLogin(delegate)
        return null
    }
}
