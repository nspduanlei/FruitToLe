package com.ap88.yg.fruittole.ui.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import kotlinx.android.synthetic.main.layout_web.*

/**
 * Created by duanlei on 2018/3/1.
 */
class TestWebDelegate : BaseDelegate() {
    override fun setLayout(): Any {
        return R.layout.layout_web
    }

    private var dialog: ProgressDialog? = null

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

            webView.loadUrl("http://192.168.7.100/#/test")
//        webView.loadUrl("https://yg.ap88.com/#/fruitCircle")
//        webView.loadUrl("https://www.baidu.com/")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        webView.settings.domStorageEnabled = true

        webView.webViewClient = WebViewClient()

        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    closeDialog()
                } else {
                    //网页正在加载，打开ProgressDialog
                    openDialog(newProgress)
                }
            }

            private fun openDialog(newProgress: Int) {
                if (dialog == null) {
                    dialog = ProgressDialog(context)
                    dialog!!.setTitle("正在加载")
                    dialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                    dialog!!.progress = newProgress
                    dialog!!.setCancelable(true)
                    dialog!!.show()
                } else {
                    dialog!!.progress = newProgress
                }
            }

            private fun closeDialog() {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.dismiss()
                    dialog = null
                }
            }
        }

        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        //不能横行滚动
        webView.isHorizontalScrollBarEnabled = false
        //不能纵向滚动
        webView.isVerticalScrollBarEnabled = false
        //允许截图
        webView.isDrawingCacheEnabled = true
        //屏蔽长按事件
        webView.setOnLongClickListener(View.OnLongClickListener { true })

        //初始化WebSettings
        val settings = webView.settings
        settings.javaScriptEnabled = true

        val ua = settings.userAgentString
        settings.userAgentString = ua + "Ap88"
        //隐藏缩放控件
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        //禁止缩放
        settings.setSupportZoom(false)
        //文件权限
        settings.allowFileAccess = true
        settings.allowFileAccessFromFileURLs = true
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowContentAccess = true
        //缓存相关
        settings.setAppCacheEnabled(true)
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
    }
}