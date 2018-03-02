package com.ap88.yg.fruittole.ui.fragments.bottom

import android.view.KeyEvent
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import org.jetbrains.anko.toast

/**
 * Created by duanlei on 2018/2/2.
 */

abstract class BottomItemDelegate : BaseDelegate(), View.OnKeyListener {
    private var mExitTime = 0L
    private val mTime = 2000

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.action == KeyEvent.ACTION_DOWN) {
            mExitTime = if ((System.currentTimeMillis() - mExitTime) > mTime) {
                _mActivity.toast("双击退出" + getString(R.string.app_name))
                System.currentTimeMillis()
            } else {
                _mActivity.finish()
                0
            }
            return true
        }
        return false
    }

    override fun onResume() {
        super.onResume()

        val rootView = view
        if (rootView != null) {
            rootView.isFocusableInTouchMode = true
            rootView.requestFocus()
            rootView.setOnKeyListener(this)
        }
    }

}