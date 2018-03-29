package com.ap88.yg.fruittole.utils;

import android.content.Context;
import com.ap88.yg.fruittole.ui.App;

/**
 * Created by duanlei on 2017/12/6.
 *
 */
public class ChannelUtils {
  /**
   * 是否生产渠道
   * @return
   */
  public static boolean isProduct() {
    return !AppUtils.getChannelId(App.Companion.getInstance().getApplicationContext(),
        "UMENG_CHANNEL").equals("dev");
  }

  public static boolean isDebug(Context context) {
    return AppUtils.getChannelId(context, "BUILD_TYPE").equals("debug");
  }

//  /**
//   * 获取baseUrl
//   * @return
//   */
//  public static String getBaseUrl() {
//    if (isProduct()) {
//      return Constants.OFFICIAL_BASE_URL;
//    } else {
//      return Constants.TEST_BASE_URL;
//    }
//  }
//
//  /**
//   * 获取用户协议地址
//   * @return
//   */
//  public static String getUserProtocolsUrl(Context context) {
//    return getBaseUrl() + Constants.USER_PROTOCOLS;
//  }
//
//  /**
//   * 获取平台协议地址
//   * @return
//   */
//  public static String getPlatformProtocolsUrl(Context context) {
//    return getBaseUrl() + Constants.PLATFORM_PROTOCOLS;
//  }

}
