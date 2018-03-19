package com.ap88.yg.fruittole.ui.activities

import android.os.Bundle
import com.ap88.yg.fruittole.ui.activities.base.ProxyActivity
import com.ap88.yg.fruittole.ui.fragments.BottomDelegate
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import qiu.niorgai.StatusBarCompat

class MainActivity : ProxyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //透明状态栏效果
        StatusBarCompat.translucentStatusBar(this, true)
    }

    override fun setRootDelegate(): BaseDelegate {
//        val webDelegateImpl = BottomDelegate()
//        Handler().postDelayed({
//            webDelegateImpl.start(WebDelegateImpl.create(ApiStores.URL_WEB))
//        }, 100)
        return BottomDelegate()
    }


}
