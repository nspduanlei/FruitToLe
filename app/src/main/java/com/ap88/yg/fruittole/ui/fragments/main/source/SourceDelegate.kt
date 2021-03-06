package com.ap88.yg.fruittole.ui.fragments.main.source

import android.os.Bundle
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.ui.fragments.bottom.BottomItemDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl

/**
 * Created by duanlei on 2018/3/1.
 *
 */
class SourceDelegate: BottomItemDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_web
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        //ll_root.setPadding(0, StateBarUtil.getStatusBarHeight(activity!!), 0, 0)

        val webDelegateImpl = WebDelegateImpl.create(ApiStores.URL_WEB + "#/findSHomeApp")
        webDelegateImpl.topDelegate = this.getParentDelegate()

        loadRootFragment(R.id.fl_contains, webDelegateImpl)
    }

}