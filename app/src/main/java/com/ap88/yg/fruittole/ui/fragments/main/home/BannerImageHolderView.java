package com.ap88.yg.fruittole.ui.fragments.main.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ap88.yg.fruittole.domain.model.BannerListBean;
import com.ap88.yg.fruittole.utils.imageload.ImageLoad;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by duanlei on 2018/2/26.
 */

class BannerImageHolderView implements Holder<BannerListBean> {
  private ImageView imageView;

  @Override
  public View createView(Context context) {
    imageView = new ImageView(context);
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    return imageView;
  }

  @Override
  public void UpdateUI(Context context, final int position, BannerListBean data) {
    ImageLoad.loadUrl(context, imageView, data.getContent());
  }
}