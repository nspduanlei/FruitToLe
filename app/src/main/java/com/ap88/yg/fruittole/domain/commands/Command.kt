package com.ap88.yg.fruittole.domain.commands

/**
 * Created by duanlei on 2018/1/30.
 */
interface Command<out T> {
    fun execute(): T
}