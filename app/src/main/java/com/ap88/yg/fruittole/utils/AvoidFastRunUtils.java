package com.ap88.yg.fruittole.utils;

/**
 * Created by duanlei on 2018/3/29.
 */

public class AvoidFastRunUtils {

  //两次点击按钮之间的点击间隔不能少于2秒
  private static final int MIN_CLICK_DELAY_TIME = 2000;
  private static long lastClickTime;

  public static boolean isFastClick() {
    boolean flag = false;
    long curClickTime = System.currentTimeMillis();
    if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
      flag = true;
    }
    lastClickTime = curClickTime;
    return flag;
  }

}
