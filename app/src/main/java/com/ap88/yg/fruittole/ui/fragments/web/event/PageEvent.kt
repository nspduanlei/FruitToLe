package com.ap88.yg.fruittole.ui.fragments.web.event

import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class PageEvent : Event() {

    override fun execute(params: String): String? {
        Toast.makeText(context, params, Toast.LENGTH_LONG).show()
        //Log.e("test0001", params)

        val url = JSON.parseObject(params).getJSONObject("data").getString("url")
        val topDelegate = delegate.topDelegate
        val webDelegate = WebDelegateImpl.create(ApiStores.URL_WEB + url)
        topDelegate.start(webDelegate)
        return null
    }
}
