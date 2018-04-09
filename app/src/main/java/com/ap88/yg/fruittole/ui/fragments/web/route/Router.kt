package com.ap88.yg.fruittole.ui.fragments.web.route

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.webkit.URLUtil
import android.webkit.WebView
import com.ap88.yg.fruittole.extensions.DelegatesExt
import com.ap88.yg.fruittole.ui.App
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import com.ap88.yg.fruittole.utils.L


/**
 * Created by duanlei on 2018/1/6.
 *
 */
class Router private constructor() {

    companion object {
        val instance: Router
            get() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = Router()
    }

    private var curUrl: String by DelegatesExt.preference(App.instance.applicationContext, "curUrl", "")

    fun handleWebUrl(delegate: WebDelegate, url: String): Boolean {
        L.e("test0001", "url-------------$url")

        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.context, url)
            return true
        }

        val topDelegate = delegate.topDelegate
        val webDelegate = WebDelegateImpl.create(url)
        topDelegate.start(webDelegate)
        return true
    }

    private fun loadWebPage(webView: WebView?, url: String) {
        if (webView != null) {
            webView.loadUrl(url)
        } else {
            throw NullPointerException("WebView is null!")
        }
    }

    private fun loadLocalPage(webView: WebView?, url: String) {
        loadWebPage(webView, "file:///android_asset/$url")
    }

    private fun loadPage(webView: WebView?, url: String) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url)
        } else {
            loadLocalPage(webView, url)
        }
    }

    fun loadPage(delegate: WebDelegate, url: String) {
        loadPage(delegate.webView, url)
    }

    private fun callPhone(context: Context?, uri: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse(uri)
        intent.data = data
        ContextCompat.startActivity(context!!, intent, null)
    }
}
