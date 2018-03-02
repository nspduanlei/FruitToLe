package com.ap88.yg.fruittole.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ap88.yg.fruittole.utils.DensityUtils;

/**
 * Created by duanlei on 2018/2/23.
 */
public class MyProgressView extends View {

  private Paint mPaintLeft;
  private Paint mPaintRight;
  private Paint mPaintText;

  private Path mPathLeft;
  private Path mPathRight;

  //看涨进度
  private int mProgress = 50;
  //左右进度间隔
  private int mInterval = 5;
  //文字上下居中，偏移
  private float yOffset;

  private float mTextPadding = 0;

  public MyProgressView(Context context) {
    this(context, null);
  }

  public MyProgressView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init(context);
  }

  private void init(Context context) {
    mPaintLeft = new Paint();
    mPaintRight = new Paint();
    mPaintText = new Paint();

    mPaintText.setTextSize(DensityUtils.sp2px(context, 16));
    mPaintText.setColor(Color.WHITE);

    mPathLeft = new Path();
    mPathRight = new Path();

    mPaintLeft.setStyle(Paint.Style.FILL);
    mPaintLeft.setColor(Color.parseColor("#ec534d"));

    mPaintRight.setStyle(Paint.Style.FILL);
    mPaintRight.setColor(Color.parseColor("#508cee"));

    mTextPadding = DensityUtils.dp2px(context, 11);

    Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
    yOffset = - (fontMetrics.ascent + fontMetrics.descent) / 2;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int width = getWidth();
    int height = getHeight();

    float proWidth = (width - mInterval) * (mProgress/100f);

    mPathLeft.reset();
    mPathLeft.lineTo(proWidth + height/2, 0);
    mPathLeft.lineTo(proWidth, height);
    mPathLeft.lineTo(0, height);
    mPathLeft.close();

    mPathRight.reset();
    mPathRight.moveTo(proWidth + mInterval + height/2, 0);
    mPathRight.lineTo(width, 0);
    mPathRight.lineTo(width, height);
    mPathRight.lineTo(proWidth + mInterval, height);
    mPathRight.close();

    canvas.drawPath(mPathLeft, mPaintLeft);
    canvas.drawPath(mPathRight, mPaintRight);


    int middle = height / 2;

    mPaintText.setTextAlign(Paint.Align.LEFT);
    canvas.drawText(mProgress + "% 人看涨", mTextPadding, middle + yOffset, mPaintText);

    mPaintText.setTextAlign(Paint.Align.RIGHT);
    canvas.drawText(100 - mProgress + "% 人看跌", width - mTextPadding,
        middle + yOffset, mPaintText);
  }

  public void setProgress(int progress) {
    mProgress = progress;
    invalidate();
  }
}
