package com.ap88.yg.fruittole.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.WindowManager

/**
 * Created by duanlei on 2018/2/2.
 */
class StateBarUtil {
    companion object {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun setStateBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= 21) {
                val decorView = activity.window.decorView
                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = option
                activity.window.statusBarColor = Color.TRANSPARENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                    context.resources.getIdentifier("status_bar_height", "dimen",
                            "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    }
}