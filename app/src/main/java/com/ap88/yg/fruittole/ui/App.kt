package com.ap88.yg.fruittole.ui

import android.app.Application
import com.ap88.yg.fruittole.extensions.DelegatesExt
import com.ap88.yg.fruittole.ui.fragments.web.event.*
import com.ap88.yg.fruittole.utils.ChannelUtils
import com.ap88.yg.fruittole.utils.L
import com.ap88.yg.fruittole.utils.T
import com.facebook.stetho.Stetho
import com.pgyersdk.crash.PgyCrashManager

/**
 * Created by duanlei on 2018/1/29.
 *
 */
class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (ChannelUtils.isDebug(this)) {
            T.isShow = true
            L.isDebug = true
        }

        //蒲公英Crash上报
        PgyCrashManager.register(this)

        Stetho.initializeWithDefaults(this)

        EventManager.getInstance().addEvent("test", TestEvent())
                .addEvent("login", LoginEvent())
                .addEvent("logout", LogoutEvent())
                .addEvent("back", BackEvent())
                .addEvent("page", PageEvent())
                .addEvent("pubCircle", PubCircleEvent())
                .addEvent("pubSD", PubSDEvent())
                .addEvent("replaceLogin", ReplaceLoginEvent())
                .addEvent("selLocation", SelLocationEvent())
                .addEvent("pageBack", com.ap88.yg.fruittole.ui.fragments.web.event.PageBackEvent())
    }
}