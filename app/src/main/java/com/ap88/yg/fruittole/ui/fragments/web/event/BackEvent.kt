package com.ap88.yg.fruittole.ui.fragments.web.event

import android.widget.Toast

/**
 * Created by duanlei on 2018/1/9.
 *
 */
class BackEvent : Event() {

    override fun execute(params: String): String? {
        Toast.makeText(context, params, Toast.LENGTH_LONG).show()
        //Log.e("test0001", params)

        delegate.pop()

        return null
    }
}
