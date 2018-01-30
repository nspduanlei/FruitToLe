package com.ap88.yg.fruittole.domain

import android.app.Activity
import android.widget.Toast

/**
 * Created by duanlei on 2018/1/26.
 */
class Person(firstName: String) {


    init {
        println("this is firstName: $firstName")
    }

    fun add(x: Int, y: Int): Int {
        return x + y
    }

    fun add1(x: Int, y: Int): Int = x + y


    fun toast(context: Activity, message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, length).show()

        context.javaClass.simpleName
    }


    fun niceToast(context: Activity, message: String,
                  tag :String = context.javaClass.simpleName,
                  length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, "[$tag] $message", length).show()
    }


}