package com.ap88.yg.fruittole.ui.fragments.main.me

import android.os.Bundle
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.ui.fragments.bottom.BottomItemDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl

/**
 * Created by duanlei on 2018/3/1.
 */
class MeDelegate: BottomItemDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_web
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        //ll_root.setPadding(0, StateBarUtil.getStatusBarHeight(activity!!), 0, 0)

        //https://yg.ap88.com/#/login

        val webDelegateImpl = WebDelegateImpl.create("http://192.168.7.100/#/pcApp")
        webDelegateImpl.topDelegate = this.getParentDelegate()

        loadRootFragment(R.id.fl_contains, webDelegateImpl)

//        loadRootFragment(R.id.fl_contains,
//                WebDelegateImpl.create("http://192.168.7.100/#/test"))
    }
}