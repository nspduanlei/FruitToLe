package com.ap88.yg.fruittole.ui.fragments.web.event

import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.utils.T
import org.greenrobot.eventbus.EventBus

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class PubSDEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)

        EventBus.getDefault().post(MessageEvent(MessageEvent.HOME_UPDATE))
        delegate.pop()
        return null
    }
}
