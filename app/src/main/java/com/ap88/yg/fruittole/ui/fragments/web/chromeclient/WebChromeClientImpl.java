package com.ap88.yg.fruittole.ui.fragments.web.chromeclient;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ap88.yg.fruittole.ui.fragments.web.WebDelegate;
import com.ap88.yg.fruittole.ui.widget.WebViewProgressBar;
import com.ap88.yg.fruittole.utils.imageselect.ImgSelUtil;

import java.io.File;
import java.util.List;

/**
 * Created by duanlei on 2018/1/8.
 *
 */
public class WebChromeClientImpl extends WebChromeClient {

  private WebViewProgressBar mWebViewProgressBar;
  private final WebDelegate DELEGATE;

  public WebChromeClientImpl(WebDelegate delegate, WebViewProgressBar webViewProgressBar) {
    DELEGATE = delegate;
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


  private ValueCallback<Uri[]> mUploadMessage;
  private ValueCallback<Uri> mUploadMessageOld;

  public static final int REQUEST_INPUT_FILE = 100;


  // For Android 3.0+
  public void openFileChooser(ValueCallback<Uri> uploadMsg) {
    Log.e("test008", "onShowFileChooser-----------------1");
    mUploadMessageOld = uploadMsg;
    selectImage();
  }

  // For Android 3.0+
  public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
    Log.e("test008", "onShowFileChooser-----------------2");
    mUploadMessageOld = uploadMsg;
    selectImage();
  }

  //For Android 4.1
  public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
    Log.e("test008", "onShowFileChooser-----------------3");
    mUploadMessageOld = uploadMsg;
    selectImage();
  }

  // For Android 5.0+
  @Override
  public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                   FileChooserParams fileChooserParams) {

    Log.e("test008", "onShowFileChooser-----------------4");
    mUploadMessage = filePathCallback;

    selectImage();
    return true;
  }

  private void selectImage() {
//    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//    i.addCategory(Intent.CATEGORY_OPENABLE);
//    i.setType("image/*");
//    if (DELEGATE.getActivity() != null) {
//      DELEGATE.getActivity().startActivityForResult(Intent.createChooser(i, "File Browser"),
//          REQUEST_INPUT_FILE);
//    }

    ImgSelUtil.gotoSelImg(DELEGATE.getActivity(), REQUEST_INPUT_FILE, false);
  }


  public void onFileChooserBack(List<String> uris) {
    if (uris == null) {
      if (mUploadMessage != null) {
        mUploadMessage.onReceiveValue(null);
        mUploadMessage = null;
      } else if (mUploadMessageOld != null) {
        mUploadMessageOld.onReceiveValue(null);
        mUploadMessageOld = null;
      }
      return;
    }

    Uri[] uriArr = new Uri[uris.size()];
    for (String path: uris) {
      uriArr[0] = Uri.fromFile(new File(path));
    }

    if (mUploadMessage != null) {
      mUploadMessage.onReceiveValue(uriArr);
      mUploadMessage = null;
    } else if (mUploadMessageOld != null) {
      mUploadMessageOld.onReceiveValue(uriArr[0]);
      mUploadMessageOld = null;
    }

  }
}
