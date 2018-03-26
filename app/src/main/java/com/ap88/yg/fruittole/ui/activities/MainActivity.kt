package com.ap88.yg.fruittole.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.ui.activities.base.PermissionActivity
import com.ap88.yg.fruittole.ui.fragments.BottomDelegate
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.chromeclient.WebChromeClientImpl
import org.greenrobot.eventbus.EventBus
import qiu.niorgai.StatusBarCompat

class MainActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //透明状态栏效果
        StatusBarCompat.translucentStatusBar(this, true)
    }

    override fun setRootDelegate(): BaseDelegate {
        return BottomDelegate()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        Log.e("test007", "onActivityResult-----------")
        if (requestCode == WebChromeClientImpl.REQUEST_INPUT_FILE && resultCode == RESULT_OK && intent != null) {
            val result = intent.data
            if (result != null) {
                Log.e("test007", "onActivityResult---------1--")
                EventBus.getDefault().post(MessageEvent(MessageEvent.CHOOSE_FILE, result))
            }
        }
    }

}
