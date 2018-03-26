package com.ap88.yg.fruittole.domain.model

import android.net.Uri

/**
 * Created by duanlei on 2018/3/23.
 */

class MessageEvent {

    companion object {
        const val USER_UPDATE = 1
        const val CHOOSE_FILE = 2
        const val CIRCLE_UPDATE = 3
        const val HOME_UPDATE = 4
    }

    var id: Int = 0
    var file: Uri? = null

    constructor(id: Int) {
        this.id = id
    }

    constructor(id: Int, file: Uri) {
        this.id = id
        this.file = file
    }
}
