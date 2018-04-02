package com.ap88.yg.fruittole.ui.fragments.web.event

import com.alibaba.fastjson.JSON
import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.ui.utils.LoginUtils
import com.ap88.yg.fruittole.utils.L
import com.ap88.yg.fruittole.utils.T
import org.greenrobot.eventbus.EventBus

/**
 * Created by duanlei on 2018/1/9.
 * 选择地址
 */
class SelLocationEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)
        L.e("test0001", params)

        val cityName = JSON.parseObject(params).getJSONObject("data").getString("name")
        LoginUtils.cityName = cityName

        EventBus.getDefault().post(MessageEvent(MessageEvent.CITY_SELECT))

        delegate.pop()
        return null
    }
}
