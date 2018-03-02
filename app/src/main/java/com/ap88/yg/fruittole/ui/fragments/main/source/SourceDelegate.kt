package com.ap88.yg.fruittole.ui.fragments.main.source

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
class SourceDelegate: BaseDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_web
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        ll_root.setPadding(0, StateBarUtil.getStatusBarHeight(activity!!), 0, 0)
        loadRootFragment(R.id.fl_contains,
                WebDelegateImpl.create("http://192.168.7.100/#/findSHome"))
    }

}