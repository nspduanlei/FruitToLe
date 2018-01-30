package com.ap88.yg.fruittole.ui

import android.app.Application
import com.ap88.yg.fruittole.extensions.DelegatesExt

/**
 * Created by duanlei on 2018/1/29.
 */
class App: Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}