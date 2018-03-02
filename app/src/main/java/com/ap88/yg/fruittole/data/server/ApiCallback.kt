package com.ap88.yg.fruittole.data.server

import android.util.Log
import com.ap88.yg.fruittole.domain.model.Result
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

    override fun onNext(t: T) {
        if (t is Result<*>) {
            if (t.succeed) {
                onSuccess(t)
            }
        }
    }

    override fun onError(e: Throwable?) {
        if (e is HttpException) {

            val code = e.code()
            var msg = e.message

            Log.d("wxl", "code=" + code)

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