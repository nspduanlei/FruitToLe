package com.ap88.yg.fruittole.data.server

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.ap88.yg.fruittole.domain.model.Result
import com.ap88.yg.fruittole.ui.App
import retrofit2.HttpException
import rx.Subscriber

/**
 * Created by duanlei on 2018/2/26.
 */
abstract class ApiCallback<T> : Subscriber<T>() {

    abstract fun onSuccess(t: T)
    abstract fun onFailure(msg: String?)
    abstract fun onFinish()


    override fun onCompleted() {
        onFinish()
    }

    @SuppressLint("ShowToast")
    override fun onNext(t: T) {
        if (t is Result<*>) {
            if (t.succeed) {
                onSuccess(t)
            } else {
                Log.e("ApiCallback", "errorMsg:" + t.errorMsg + ", errorCode:" + t.errorCode)
            }
        } else {
            Log.e("ApiCallback", "数据格式错误----------------")
        }
    }

    @SuppressLint("ShowToast")
    override fun onError(e: Throwable?) {
        if (e != null) {
            Log.e("ApiCallback", "onError----------------" + e.message)
        }

        Toast.makeText(App.instance.applicationContext, "网络错误", Toast.LENGTH_SHORT)

        if (e is HttpException) {
            val code = e.code()
            var msg = e.message

            Log.d("wxl", "code=$code")

            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(e.toString())
        }

        onFinish()
    }
}