package com.ap88.yg.fruittole.utils.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.ap88.yg.fruittole.utils.StringUtils;
import com.bumptech.glide.Glide;

/**
 * Created by duanlei on 2016/11/8.
 *
 */
public class ImageLoad {

  public static void loadUrl(Context context, ImageView iv, String url) {
    if (StringUtils.isNullOrEmpty(url)) {
      return;
    }
    Glide.with(context)
        .load(url)
        .crossFade()
        .into(iv);
  }

  public static void loadUrlDefault(Context context, ImageView iv, String url, int resId) {
    if (StringUtils.isNullOrEmpty(url)) {
      return;
    }
    Glide.with(context)
        .load(url)
        .placeholder(resId)
        .crossFade()
        .into(iv);
  }

  public static void loadLargeUrl(Context context, ImageView iv, String url) {
    if (StringUtils.isNullOrEmpty(url)) {
      return;
    }
    Glide.with(context)
        .load(url)
        .fitCenter()
        .into(iv);
  }

  public static void loadUrlCircle(Context context, ImageView iv, String url) {
    if (StringUtils.isNullOrEmpty(url)) {
      return;
    }
    Glide.with(context)
        .load(url)
        .transform(new GlideCircleTransform(context))
        .into(iv);
  }

  public static void loadUrlRound(Context context, ImageView iv, String url, int dp) {
    if (StringUtils.isNullOrEmpty(url)) {
      return;
    }
    Glide.with(context)
        .load(url)
        .centerCrop()
        .crossFade()
        .transform(new GlideRoundTransform(context, dp))
        .into(iv);
  }
}

