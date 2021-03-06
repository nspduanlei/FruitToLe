package com.ap88.yg.fruittole.ui.fragments.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate;
import com.ap88.yg.fruittole.ui.fragments.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by duanlei on 2018/1/5.
 */

public abstract class WebDelegate extends BaseDelegate implements IWebViewInitializer {

  private WebView mWebView = null;

  private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
  private String mUrl = null;
  private boolean mIsWebViewAvailable = false;
  private BaseDelegate mTopDelegate = null;

  public abstract IWebViewInitializer setInitializer();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Bundle args = getArguments();
    mUrl = args.getString(RouteKeys.URL.name());

    initWebView();
  }

  @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
  private void initWebView() {
    if (mWebView != null) {
      mWebView.removeAllViews();
      mWebView.destroy();
    } else {
      final IWebViewInitializer initializer = setInitializer();
      if (initializer != null) {
        final WeakReference<WebView> webViewWeakReference = new WeakReference<>
            (new WebView(getContext()), WEB_VIEW_QUEUE);
        mWebView = webViewWeakReference.get();

        mWebView = initializer.initWebView(mWebView);
        mWebView.setWebViewClient(initializer.initWebViewClient());
        mWebView.setWebChromeClient(initializer.initWebChromeClient());

        mWebView.addJavascriptInterface(WebInterface.create(this), "ap88");
        mIsWebViewAvailable = true;
      } else {
        throw new NullPointerException("Initializer is null!");
      }
    }
  }

  public void setTopDelegate(BaseDelegate delegate) {
    mTopDelegate = delegate;
  }

  public BaseDelegate getTopDelegate() {
    if (mTopDelegate == null) {
      mTopDelegate = this;
    }
    return mTopDelegate;
  }

  public WebView getWebView() {
    if (mWebView == null) {
      return null;
      //throw new NullPointerException("WebView IS NULL!");
    }
    return mIsWebViewAvailable ? mWebView : null;
  }

  public String getUrl() {
    if (mUrl == null) {
      throw new NullPointerException("URL IS NULL!");
    }
    return mUrl;
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mWebView != null) {
      mWebView.onPause();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mWebView != null) {
      mWebView.onResume();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mIsWebViewAvailable = false;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mWebView != null) {
      mWebView.removeAllViews();
      mWebView.destroy();
      mWebView = null;
    }
  }
}
