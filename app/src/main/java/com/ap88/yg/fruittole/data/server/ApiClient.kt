package com.ap88.yg.fruittole.data.server

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by duanlei on 2018/2/26.
 *
 */
object ApiClient {

    fun retrofit(): ApiStores {
        val builder = OkHttpClient.Builder()

//        if (BuildConfig.DEBUG) {
//            //log 信息拦截器
//            val loggingInterface = HttpLoggingInterceptor()
//            loggingInterface.level = HttpLoggingInterceptor.Level.BODY
//
//            //设置Debug Log模式
//            builder.addInterceptor(loggingInterface)
//        }

        val okHttpClient = builder
                .addNetworkInterceptor(StethoInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiStores::class.java)
    }

}