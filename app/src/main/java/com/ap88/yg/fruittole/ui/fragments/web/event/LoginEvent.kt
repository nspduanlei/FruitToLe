package com.ap88.yg.fruittole.ui.fragments.web.event

import com.alibaba.fastjson.JSON
import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.ui.utils.LoginUtils
import com.ap88.yg.fruittole.utils.T
import org.greenrobot.eventbus.EventBus

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class LoginEvent : Event() {

    override fun execute(params: String): String? {

        T.showShort(context, params)

        val token = JSON.parseObject(params).getJSONObject("data").getString("token")
        LoginUtils.token = token

        EventBus.getDefault().post(MessageEvent(MessageEvent.USER_UPDATE))

        return null
    }
}
