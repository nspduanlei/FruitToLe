package com.ap88.yg.fruittole.ui.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import com.ap88.yg.fruittole.domain.model.MessageEvent
import com.ap88.yg.fruittole.ui.activities.base.PermissionActivity
import com.ap88.yg.fruittole.ui.fragments.BottomDelegate
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.web.chromeclient.WebChromeClientImpl
import com.ap88.yg.fruittole.utils.AppUtils
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.yuyh.library.imgsel.ImgSelActivity
import org.greenrobot.eventbus.EventBus
import qiu.niorgai.StatusBarCompat


class MainActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //透明状态栏效果
        StatusBarCompat.translucentStatusBar(this, true)

        //版本更新
        Handler().postDelayed({
            updateVersion()
        }, 2000)
    }

    private fun updateVersion() {
        PgyUpdateManager.register(this, object: UpdateManagerListener(){
            override fun onNoUpdateAvailable() {}
            override fun onUpdateAvailable(result: String?) {
                // 将新版本信息封装到AppBean中
                val appBean = getAppBeanFromString(result)

                val verCode = appBean.versionCode.toInt()
                if (verCode > AppUtils.getVersionCode(this@MainActivity)) {
                    AlertDialog.Builder(this@MainActivity)
                            .setTitle("发现新更新 v" + appBean.versionName)
                            .setMessage(appBean.releaseNote)
                            .setPositiveButton(
                                    "确定",
                                    DialogInterface.OnClickListener { _, _ ->

                                        startDownloadTask(
                                                this@MainActivity,
                                                appBean.downloadURL)

                                    }
                            )
                            .setNegativeButton(
                                    "取消",
                                    DialogInterface.OnClickListener { dialog, _ ->
                                        dialog.dismiss()
                                    }
                            ).show()
                }

            }

        })
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


    override fun onDestroy() {
        super.onDestroy()
        PgyUpdateManager.unregister()
    }

}
