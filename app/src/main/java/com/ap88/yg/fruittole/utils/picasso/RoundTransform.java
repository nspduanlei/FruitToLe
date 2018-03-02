package com.ap88.yg.fruittole.utils.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.ap88.yg.fruittole.utils.DensityUtils;
import com.squareup.picasso.Transformation;

/**
 * Created by duanlei on 2018/2/26.
 * 实现圆角图片
 */
public class RoundTransform implements Transformation {

  private Context mContext;
  private int mRadius;

  public RoundTransform(Context context, int radius) {
    mContext = context;
    mRadius = radius;
  }

  @Override
  public Bitmap transform(Bitmap source) {

    int widthLight = source.getWidth();
    int heightLight = source.getHeight();
    int radius = DensityUtils.dp2px(mContext, mRadius); // 圆角半径

    Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(output);
    Paint paintColor = new Paint();
    paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

    RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

    canvas.drawRoundRect(rectF, radius, radius, paintColor);
//        canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);

    Paint paintImage = new Paint();
    paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
    canvas.drawBitmap(source, 0, 0, paintImage);
    source.recycle();
    return output;
  }

  @Override
  public String key() {
    return "roundcorner";
  }

}
