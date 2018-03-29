package com.ap88.yg.fruittole.ui.fragments.web.event

import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import com.ap88.yg.fruittole.utils.AvoidFastRunUtils
import com.ap88.yg.fruittole.utils.T

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class PageBackEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)

        val webDelegate = delegate as WebDelegateImpl

        if (AvoidFastRunUtils.isFastClick()) {
            webDelegate.pageBack()
        }

        return null
    }
}
