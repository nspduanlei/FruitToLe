package com.ap88.yg.fruittole.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ap88.yg.fruittole.R;

/**
 * Created by duanlei on 2018/2/28.
 */

public class TestActivity extends Activity{

  private ProgressDialog dialog;
  private WebView webView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.layout_web);

    webView= findViewById(R.id.webView);

//    webView.loadUrl("http://192.168.7.100/#/test");
    webView.loadUrl("https://yg.ap88.com/#/login");
//    webView.loadUrl("https://www.baidu.com/");


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    webView.getSettings().setDomStorageEnabled(true);

    webView.setWebViewClient(new WebViewClient());

    webView.setWebChromeClient(new WebChromeClient() {

      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        //newProgress   1-100之间的整数
        if (newProgress == 100) {
          //页面加载完成，关闭ProgressDialog
          closeDialog();
        } else {
          //网页正在加载，打开ProgressDialog
          openDialog(newProgress);
        }
      }

      private void openDialog(int newProgress) {
        if (dialog == null) {
          dialog = new ProgressDialog(TestActivity.this);
          dialog.setTitle("正在加载");
          dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
          dialog.setProgress(newProgress);
          dialog.setCancelable(true);
          dialog.show();
        } else {
          dialog.setProgress(newProgress);
        }
      }

      private void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
          dialog.dismiss();
          dialog = null;
        }
      }
    });

    setWebView();
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  private void setWebView() {
    //不能横行滚动
    webView.setHorizontalScrollBarEnabled(false);
    //不能纵向滚动
    webView.setVerticalScrollBarEnabled(false);
    //允许截图
    webView.setDrawingCacheEnabled(true);
    //屏蔽长按事件
    webView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        return true;
      }
    });

    //初始化WebSettings
    final WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);

    final String ua = settings.getUserAgentString();
    settings.setUserAgentString(ua + "Ap88");
    //隐藏缩放控件
    settings.setBuiltInZoomControls(false);
    settings.setDisplayZoomControls(false);
    //禁止缩放
    settings.setSupportZoom(false);
    //文件权限
    settings.setAllowFileAccess(true);
    settings.setAllowFileAccessFromFileURLs(true);
    settings.setAllowUniversalAccessFromFileURLs(true);
    settings.setAllowContentAccess(true);
    //缓存相关
    settings.setAppCacheEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setDatabaseEnabled(true);
    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
  }
}
