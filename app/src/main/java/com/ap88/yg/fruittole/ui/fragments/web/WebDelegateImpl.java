package com.ap88.yg.fruittole.ui.fragments.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ap88.yg.fruittole.ui.fragments.web.chromeclient.WebChromeClientImpl;
import com.ap88.yg.fruittole.ui.fragments.web.client.WebViewClientImpl;
import com.ap88.yg.fruittole.ui.fragments.web.route.RouteKeys;
import com.ap88.yg.fruittole.ui.fragments.web.route.Router;
import com.ap88.yg.fruittole.ui.widget.WebViewProgressBar;

/**
 * Created by duanlei on 2018/1/6.
 */
public class WebDelegateImpl extends WebDelegate {

  //进度条
  private WebViewProgressBar mProgressBar;

  public static WebDelegateImpl create(String url) {
    final Bundle args = new Bundle();
    args.putString(RouteKeys.URL.name(), url);
    final WebDelegateImpl delegate = new WebDelegateImpl();
    delegate.setArguments(args);
    return delegate;
  }

  @Override
  public Object setLayout() {
    return getWebView();
  }

  @Override
  public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    if (getUrl() != null) {
      this.getWebView().addView(mProgressBar);
      //用原生的方式模拟Web跳转并进行页面加载
      Router.getInstance().loadPage(this, getUrl());
    }
  }

  @Override
  public IWebViewInitializer setInitializer() {
    return this;
  }

  @Override
  public WebView initWebView(WebView webView) {
    return new WebViewInitializer().createWebView(webView);
  }

  @Override
  public WebViewClient initWebViewClient() {
    return new WebViewClientImpl(this);
  }

  @Override
  public WebChromeClient initWebChromeClient() {
    mProgressBar = new WebViewProgressBar(getContext());
    mProgressBar.setLayoutParams(new ViewGroup.LayoutParams
        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    //刚开始时候进度条不可见
    mProgressBar.setVisibility(View.GONE);

    return new WebChromeClientImpl(mProgressBar);
  }
}
