package com.ap88.yg.fruittole.ui.utils

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl

/**
 * Created by duanlei on 2018/3/5.
 */

object DialogUtils {

    /**
     * 单选按钮dialog
     * @param activity
     * @param msg
     */
    fun showSingleButtonDiaLog(activity: Activity, msg: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(msg)
        builder.setNegativeButton("知道啦") { dialog, _ -> dialog.dismiss() }
        val noticeDialog = builder.create()
        noticeDialog.show()
    }

    /**
     * 单选按钮dialog
     * @param activity
     * @param msg
     */
    fun showLoginAlertDiaLog(delegate: BaseDelegate) {
        if (delegate.activity == null) {
            return
        }
        val builder = AlertDialog.Builder(delegate.activity!!)
        builder.setMessage("请先登录")
        builder.setNegativeButton("取消") {
            dialog, _ -> dialog.dismiss()
        }
        builder.setPositiveButton("确定") {
            dialog, _ ->
            delegate.start(WebDelegateImpl.create("https://yg.ap88.com/#/login"))
            dialog.dismiss()
        }
        val noticeDialog = builder.create()
        noticeDialog.show()
    }

}
