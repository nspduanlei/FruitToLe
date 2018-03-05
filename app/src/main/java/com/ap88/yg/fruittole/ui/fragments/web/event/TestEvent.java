package com.ap88.yg.fruittole.ui.fragments.web.event;

import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by duanlei on 2018/1/9.
 *
 */
public class TestEvent extends Event {
  @Override
  public String execute(String params) {
    Toast.makeText(getContext(), params, Toast.LENGTH_LONG).show();

    final WebView webView = getWebView();
    webView.post(new Runnable() {
      @Override
      public void run() {
//        webView.evaluateJavascript("nativeCall()", null);
        webView.loadUrl("javascript:nativeCall()");
      }
    });

    return null;
  }
}
