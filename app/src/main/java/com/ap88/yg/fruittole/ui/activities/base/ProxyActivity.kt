package com.ap88.yg.fruittole.ui.activities.base

import android.os.Bundle
import android.support.v7.widget.ContentFrameLayout
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by duanlei on 2018/2/2.
 *
 */
abstract class ProxyActivity: SupportActivity() {
    abstract fun setRootDelegate(): BaseDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    private fun initContainer(savedInstanceState: Bundle?) {
        val container = ContentFrameLayout(this)
        container.id = R.id.delegate_container

        setContentView(container)

        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }
}