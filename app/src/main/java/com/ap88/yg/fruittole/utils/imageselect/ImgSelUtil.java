package com.ap88.yg.fruittole.utils.imageselect;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.ap88.yg.fruittole.R;
import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

/**
 * Created by duanlei on 2017/7/6.
 *
 */
public class ImgSelUtil {

  // 自定义图片加载器
  private static ImageLoader loader = new ImageLoader() {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
      Glide.with(context).load(path).into(imageView);
    }
  };

  public static void gotoSelImg(Activity context, int requestCode, boolean isMultiSel) {
    // 自由配置选项
    ImgSelConfig config = new ImgSelConfig.Builder(context, loader)
        // 是否多选, 默认true
        .multiSelect(isMultiSel)
        // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
        .rememberSelected(false)
        // “确定”按钮背景色
        .btnBgColor(Color.GRAY)
        // “确定”按钮文字颜色
        .btnTextColor(Color.BLUE)
        // 使用沉浸式状态栏
        .statusBarColor(Color.parseColor("#3F51B5"))
        // 返回图标ResId
        .backResId(R.drawable.arrow_back)
        // 标题
        .title("图片")
        // 标题文字颜色
        .titleColor(Color.WHITE)
        // TitleBar背景色
        .titleBgColor(Color.parseColor("#3F51B5"))
        // 裁剪大小。needCrop为true的时候配置
        .cropSize(1, 1, 200, 200)
        .needCrop(false)
        // 第一个是否显示相机，默认true
        .needCamera(true)
        // 最大选择图片数量，默认9
        .maxNum(9)
        .allImagesText("图片")
        .build();
    // 跳转到图片选择器
    ImgSelActivity.startActivity(context, config, requestCode);
  }


  public static void gotoSelImgCrop(Activity context, int requestCode, boolean isMultiSel) {
    // 自由配置选项
    ImgSelConfig config = new ImgSelConfig.Builder(context, loader)
        // 是否多选, 默认true
        .multiSelect(isMultiSel)
        // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
        .rememberSelected(false)
        // “确定”按钮背景色
        .btnBgColor(Color.GRAY)
        // “确定”按钮文字颜色
        .btnTextColor(Color.BLUE)
        // 使用沉浸式状态栏
        .statusBarColor(Color.parseColor("#3F51B5"))
        // 返回图标ResId
        .backResId(R.drawable.arrow_back)
        // 标题
        .title("图片")
        // 标题文字颜色
        .titleColor(Color.WHITE)
        // TitleBar背景色
        .titleBgColor(Color.parseColor("#3F51B5"))
        // 裁剪大小。needCrop为true的时候配置
        .cropSize(1, 1, 400, 400)
        .needCrop(true)
        // 第一个是否显示相机，默认true
        .needCamera(true)
        // 最大选择图片数量，默认9
        .maxNum(9)
        .allImagesText("图片")
        .build();
    // 跳转到图片选择器
    ImgSelActivity.startActivity(context, config, requestCode);
  }




}
