package com.ap88.yg.fruittole.domain.model

/**
 * Created by duanlei on 2018/3/23.
 *
 */
class MessageEvent {

    companion object {
        const val USER_UPDATE = 1
        const val CHOOSE_FILE = 2
        const val CIRCLE_UPDATE = 3
        const val HOME_UPDATE = 4
        const val CITY_SELECT = 5
    }

    var id: Int = 0
    var file: List<String>? = null

    constructor(id: Int) {
        this.id = id
    }

    constructor(id: Int, file: List<String>?) {
        this.id = id
        this.file = file
    }

}
