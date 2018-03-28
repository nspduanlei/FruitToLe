package com.ap88.yg.fruittole.ui.fragments.web.event

import com.ap88.yg.fruittole.utils.T

/**
 * Created by duanlei on 2018/1/9.
 * 选择地址
 */
class SelLocationEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)

        delegate.pop()

        return null
    }
}
