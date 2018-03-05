package com.ap88.yg.fruittole.ui.fragments.web.route

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebView
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl


/**
 * Created by duanlei on 2018/1/6.
 */

class Router private constructor() {

    companion object {
        val instance: Router
            get() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = Router()
    }

    //private var curUrl: String by DelegatesExt.preference(App.instance.applicationContext, "curUrl", "")

    fun handleWebUrl(delegate: WebDelegate, url: String): Boolean {
        var url = url
        Log.e("test0001", "url--------------" + url)

        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.context, url)
            return true
        }

//        if (url.contains("page:")) {
//            url = url.replaceFirst("page".toRegex(), "http")
//            Log.e("test0001", "url replace-----------" + url)
//            //      return true;
//        }
//        Log.e("test0001", "curUrl--------------" + curUrl)
//        if (url == curUrl) {
//           return true
//        }
//        curUrl = url

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
        loadWebPage(webView, "file:///android_asset/" + url)
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
