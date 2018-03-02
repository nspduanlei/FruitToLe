package com.ap88.yg.fruittole.ui.fragments.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by duanlei on 2018/1/5.
 */

public interface IWebViewInitializer {

  WebView initWebView(WebView webView);
  WebViewClient initWebViewClient();
  WebChromeClient initWebChromeClient();
}
