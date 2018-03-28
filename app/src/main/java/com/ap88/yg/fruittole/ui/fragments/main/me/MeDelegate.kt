package com.ap88.yg.fruittole.ui.fragments.main.me

import android.os.Bundle
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.ui.fragments.bottom.BottomItemDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by duanlei on 2018/3/1.
 *
 */
class MeDelegate: BottomItemDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_web
    }

    private lateinit var webDelegateImpl: WebDelegateImpl

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        webDelegateImpl = WebDelegateImpl.create(ApiStores.URL_WEB + "#/pcApp")
        webDelegateImpl.topDelegate = this.getParentDelegate()
        loadRootFragment(R.id.fl_contains, webDelegateImpl)

        EventBus.getDefault().register(this)
    }

    private fun update() {
        webDelegateImpl.refreshUrl()
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onMessageEvent(event: MessageEvent) {
        if (event.id == MessageEvent.USER_UPDATE) {
            update()
        }
    }

}