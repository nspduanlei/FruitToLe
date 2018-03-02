package com.ap88.yg.fruittole.ui.fragments.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.ap88.yg.fruittole.ui.fragments.web.WebDelegate;

/**
 * Created by duanlei on 2018/1/8.
 */
public abstract class Event implements IEvent {
  private Context mContext = null;
  private String mAction = null;
  private WebDelegate mDelegate = null;
  private String mUrl = null;
  private WebView mWebView = null;

  public WebView getWebView() {
    return mWebView;
  }

  public void setWebView(WebView webView) {
    mWebView = webView;
  }

  public Context getContext() {
    return mContext;
  }

  public void setContext(Context context) {
    mContext = context;
  }

  public String getAction() {
    return mAction;
  }

  public void setAction(String action) {
    mAction = action;
  }

  public WebDelegate getDelegate() {
    return mDelegate;
  }

  public void setDelegate(WebDelegate delegate) {
    mDelegate = delegate;
  }

  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String url) {
    mUrl = url;
  }
}
