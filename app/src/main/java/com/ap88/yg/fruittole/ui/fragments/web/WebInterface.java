package com.ap88.yg.fruittole.ui.fragments.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.ap88.yg.fruittole.ui.fragments.web.event.Event;
import com.ap88.yg.fruittole.ui.fragments.web.event.EventManager;

/**
 * Created by duanlei on 2018/1/6.
 *
 */
public final class WebInterface {
  private final WebDelegate DELEGATE;

  private WebInterface(WebDelegate delegate) {
    this.DELEGATE = delegate;
  }

  public static WebInterface create(WebDelegate delegate) {
    return new WebInterface(delegate);
  }

  @JavascriptInterface
  public String event(String params) {

    final String action = JSON.parseObject(params).getString("action");
    final Event event = EventManager.getInstance().createEvent(action);
    if (event != null) {
      event.setAction(action);
      event.setDelegate(DELEGATE);
      event.setContext(DELEGATE.getContext());
      event.setUrl(DELEGATE.getUrl());
      event.setWebView(DELEGATE.getWebView());
      return event.execute(params);
    }

    return null;
  }


}
