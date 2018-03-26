package com.ap88.yg.fruittole.ui.fragments.web.event

import com.alibaba.fastjson.JSON
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import com.ap88.yg.fruittole.utils.T

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class PageEvent : Event() {

    override fun execute(params: String): String? {
        T.showShort(context, params)

        val url = JSON.parseObject(params).getJSONObject("data").getString("url")
        val topDelegate = delegate.topDelegate
        val webDelegate = WebDelegateImpl.create(ApiStores.URL_WEB + url)
        topDelegate.start(webDelegate)
        return null
    }
}
