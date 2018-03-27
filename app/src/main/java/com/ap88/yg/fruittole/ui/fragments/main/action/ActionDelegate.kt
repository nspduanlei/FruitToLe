package com.ap88.yg.fruittole.ui.fragments.main.action

import android.os.Bundle
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import com.ap88.yg.fruittole.ui.utils.LoginUtils.checkLogin
import kotlinx.android.synthetic.main.delegate_action.*

/**
 * Created by duanlei on 2018/2/2.
 *
 */
class ActionDelegate: BaseDelegate() {

    override fun setLayout() = R.layout.delegate_action

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

        tv_req_buy.setOnClickListener{
            if (checkLogin(getParentDelegate())) {
                this.start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/pbuy?Info=BUY"))
            }
        }

        tv_supply.setOnClickListener{
            if (checkLogin(this)) {
                this.start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/psell?Info=SELL"))
            }
        }

        iv_close.setOnClickListener {
            this.pop()
        }
    }


}
