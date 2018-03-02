package com.ap88.yg.fruittole.ui.fragments.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate;
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegate;
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by duanlei on 2018/1/6.
 */

public class Router {

  private Router() {

  }

  private static class Holder {
    private static final Router INSTANCE = new Router();
  }

  public static Router getInstance() {
    return Holder.INSTANCE;
  }

  public final boolean handleWebUrl(WebDelegate delegate, String url) {
    //如果是电话协议
    if (url.contains("tel:")) {
      callPhone(delegate.getContext(), url);
      return true;
    }

//    if (url.contains("page:")) {
//      Log.e("test0001", "url--------------" + url);
//      Log.e("test0001", "preFragment-----------"  + delegate.getPreFragment());
//      return true;
//    }

    final BaseDelegate topDelegate = delegate.getTopDelegate();

    Log.e("test0001", "getTopDelegate-----------"  + topDelegate);

    final WebDelegateImpl webDelegate = WebDelegateImpl.Companion.create(url);
    topDelegate.start(webDelegate);
    return true;
  }

  private void loadWebPage(WebView webView, String url) {
    if (webView != null) {

      Map<String, String> extraHeaders;
      extraHeaders = new HashMap<>();
      extraHeaders.put("device", "Android");//设备标识(前面是key，后面是value)
//      extraHeaders.put("version", "1.0");//版本号(前面是key，后面是value)
      webView.loadUrl(url, extraHeaders);

//      webView.loadUrl(url);
    } else {
      throw new NullPointerException("WebView is null!");
    }
  }

  private void loadLocalPage(WebView webView, String url) {
    loadWebPage(webView, "file:///android_asset/" + url);
  }

  public void loadPage(WebView webView, String url) {
    if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
      loadWebPage(webView, url);
    } else {
      loadLocalPage(webView, url);
    }
  }

  public void loadPage(WebDelegate delegate, String url) {
    loadPage(delegate.getWebView(), url);
  }

  private void callPhone(Context context, String uri) {
    final Intent intent = new Intent(Intent.ACTION_DIAL);
    final Uri data = Uri.parse(uri);
    intent.setData(data);
    ContextCompat.startActivity(context, intent, null);
  }
}
