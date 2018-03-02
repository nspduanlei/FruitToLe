package com.ap88.yg.fruittole.ui.utils;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ap88.yg.fruittole.R;
import com.ap88.yg.fruittole.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by duanlei on 2017/12/8.
 */

public class MyLoader {

  private static final int LOADER_SIZE_SCALE = 8;
  private static final int LOADER_OFFSET_SCALE = 10;

  private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();


  public static void showLoading(Context context) {
    final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
    //点击返回对话框不消失
    dialog.setCancelable(false);

    dialog.setContentView(R.layout.loading);

    int deviceWidth = ScreenUtils.getScreenWidth(context);
    int deviceHeight = ScreenUtils.getScreenHeight(context);

    final Window dialogWindow = dialog.getWindow();
    if (dialogWindow != null) {
      WindowManager.LayoutParams lp = dialogWindow.getAttributes();
      lp.width = deviceWidth / LOADER_SIZE_SCALE;
      lp.height = deviceHeight / LOADER_SIZE_SCALE;
      lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
      lp.gravity = Gravity.CENTER;
    }
    LOADERS.add(dialog);
    dialog.show();
  }

  public static void stopLoading() {
    for (AppCompatDialog dialog : LOADERS) {
      if (dialog != null) {
        if (dialog.isShowing()) {
          dialog.cancel();
          dialog.dismiss();
        }
      }
    }
  }

}
