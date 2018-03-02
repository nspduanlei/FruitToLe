package com.ap88.yg.fruittole.ui.fragments.web.chromeclient;

import android.os.Handler;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ap88.yg.fruittole.ui.widget.WebViewProgressBar;

/**
 * Created by duanlei on 2018/1/8.
 */

public class WebChromeClientImpl extends WebChromeClient {


  private WebViewProgressBar mWebViewProgressBar;

  public WebChromeClientImpl(WebViewProgressBar webViewProgressBar) {
    mWebViewProgressBar = webViewProgressBar;
  }

  @Override
  public void onProgressChanged(WebView view, int newProgress) {
    if (newProgress == 100) {
      mWebViewProgressBar.setProgress(100);
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          mWebViewProgressBar.setVisibility(View.GONE);
        }
      }, 200);//0.2秒后隐藏进度条
    } else if (mWebViewProgressBar.getVisibility() == View.GONE) {
      mWebViewProgressBar.setVisibility(View.VISIBLE);
    }
    //设置初始进度10，这样会显得效果真一点，总不能从1开始吧
    if (newProgress < 10) {
      newProgress = 10;
    }
    //不断更新进度
    mWebViewProgressBar.setProgress(newProgress);
    super.onProgressChanged(view, newProgress);
  }

  @Override
  public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
    return super.onJsAlert(view, url, message, result);
  }
}
