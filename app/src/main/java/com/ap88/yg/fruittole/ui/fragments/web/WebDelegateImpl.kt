package com.ap88.yg.fruittole.ui.fragments.web

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.ap88.yg.fruittole.ui.fragments.web.chromeclient.WebChromeClientImpl
import com.ap88.yg.fruittole.ui.fragments.web.client.WebViewClientImpl
import com.ap88.yg.fruittole.ui.fragments.web.route.RouteKeys
import com.ap88.yg.fruittole.ui.fragments.web.route.Router
import com.ap88.yg.fruittole.ui.widget.WebViewProgressBar
import com.ap88.yg.fruittole.utils.StateBarUtil

/**
 * Created by duanlei on 2018/1/6.
 */
class WebDelegateImpl : WebDelegate() {

    //进度条
    private var mProgressBar: WebViewProgressBar? = null

    override fun setLayout(): Any {
        return webView
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        if (url != null) {
            this.webView!!.addView(mProgressBar)

            val lParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            lParams.setMargins(0, StateBarUtil.getStatusBarHeight(activity!!),
                    0, 0)
            rootView.layoutParams = lParams

            //用原生的方式模拟Web跳转并进行页面加载
            Router.instance.loadPage(this, url)
        }
    }

    override fun setInitializer(): IWebViewInitializer {
        return this
    }

    override fun initWebView(webView: WebView): WebView {
        return WebViewInitializer().createWebView(webView)
    }

    override fun initWebViewClient(): WebViewClient {
        return WebViewClientImpl(this)
    }

    override fun initWebChromeClient(): WebChromeClient {
        mProgressBar = WebViewProgressBar(context)
        mProgressBar!!.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //刚开始时候进度条不可见
        mProgressBar!!.visibility = View.GONE

        return WebChromeClientImpl(mProgressBar)
    }

    companion object {
        fun create(url: String): WebDelegateImpl {
            val args = Bundle()
            args.putString(RouteKeys.URL.name, url)
            val delegate = WebDelegateImpl()
            delegate.arguments = args
            return delegate
        }
    }
}
