package com.ap88.yg.fruittole.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by duanlei on 2018/2/2.
 * 画弧形
 */
class ArcView @JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0) :
        View(context, attrs, defStyleAttr) {

    private val mPaint = Paint()
    private val mPath = Path()

    init {
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        mPath.moveTo(0f, height.toFloat())
        mPath.quadTo((width/2).toFloat(), 0f,
                width.toFloat(), height.toFloat())

        canvas.drawPath(mPath, mPaint)
    }

}