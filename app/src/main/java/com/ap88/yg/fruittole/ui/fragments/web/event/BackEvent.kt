package com.ap88.yg.fruittole.ui.fragments.web.event

import com.ap88.yg.fruittole.utils.AvoidFastRunUtils
import com.ap88.yg.fruittole.utils.T

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class BackEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)

        if (AvoidFastRunUtils.isFastClick()) {
            delegate.pop()
        }

        return null
    }
}
