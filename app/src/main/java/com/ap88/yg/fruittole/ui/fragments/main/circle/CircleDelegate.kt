package com.ap88.yg.fruittole.ui.fragments.main.circle

import android.os.Bundle
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import com.ap88.yg.fruittole.utils.StateBarUtil
import kotlinx.android.synthetic.main.delegate_web.*

/**
 * Created by duanlei on 2018/3/1.
 */
class CircleDelegate : BaseDelegate() {

    private lateinit var webDelegateImpl: WebDelegateImpl

    override fun setLayout(): Any {
        return R.layout.delegate_web
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        ll_root.setPadding(0, StateBarUtil.getStatusBarHeight(activity!!), 0, 0)
        webDelegateImpl = WebDelegateImpl.create("http://192.168.7.100/#/fruitCircle")
        loadRootFragment(R.id.fl_contains, webDelegateImpl)
    }
}