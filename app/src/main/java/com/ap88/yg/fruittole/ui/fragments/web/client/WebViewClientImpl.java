package com.ap88.yg.fruittole.ui.fragments.web.client;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ap88.yg.fruittole.data.server.ApiStores;
import com.ap88.yg.fruittole.extensions.DelegatesExt;
import com.ap88.yg.fruittole.ui.fragments.web.IPageLoadListener;
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegate;
import com.ap88.yg.fruittole.ui.fragments.web.route.Router;


/**
 * Created by duanlei on 2018/1/6.
 */
public class WebViewClientImpl extends WebViewClient {

  private final WebDelegate DELEGATE;
  private IPageLoadListener mIPageLoadListener;

  public void setIPageLoadListener(IPageLoadListener IPageLoadListener) {
    mIPageLoadListener = IPageLoadListener;
  }

  public WebViewClientImpl(WebDelegate delegate) {
    this.DELEGATE = delegate;
  }

  @Override
  public boolean shouldOverrideUrlLoading(WebView view, String url) {
    return Router.Companion.getInstance().handleWebUrl(DELEGATE, url);
  }

  //获取浏览器cookie
  private void syncCookie() {
    final CookieManager manager = CookieManager.getInstance();
    //这里的Cookie和API请求的Cookie是不一样的，这个在网页不可见
    final String webHost = ApiStores.API_SERVER_URL;
    if (manager.hasCookies()) {
      final String cookieStr = manager.getCookie(webHost);
      if (cookieStr != null && !cookieStr.equals("")) {

        if (DELEGATE.getContext() != null) {
          DelegatesExt.INSTANCE.preference(DELEGATE.getContext(), "cookie", cookieStr);
        }
      }
    }
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    super.onPageStarted(view, url, favicon);
    if (mIPageLoadListener != null) {
      mIPageLoadListener.onLoadStart();
    }
//    MyLoader.showLoading(DELEGATE.getContext());
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    syncCookie();
    if (mIPageLoadListener != null) {
      mIPageLoadListener.onLoadEnd();
    }

    //关闭图片加载阻塞
    view.getSettings().setBlockNetworkImage(false);


//    MyLoader.stopLoading();
  }
}
