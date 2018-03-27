package com.ap88.yg.fruittole.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.ui.activities.base.PermissionActivity
import com.ap88.yg.fruittole.ui.fragments.BottomDelegate
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.chromeclient.WebChromeClientImpl
import com.yuyh.library.imgsel.ImgSelActivity
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

        if (requestCode == WebChromeClientImpl.REQUEST_INPUT_FILE) {

            val result = if (intent == null || resultCode != Activity.RESULT_OK) null else
                intent.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT)

            Log.e("test008", "onActivityResult-----------$result")
            EventBus.getDefault().post(MessageEvent(MessageEvent.CHOOSE_FILE, result))
        }
    }

}
