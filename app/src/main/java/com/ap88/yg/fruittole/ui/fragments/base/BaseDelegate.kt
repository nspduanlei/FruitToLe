package com.ap88.yg.fruittole.ui.fragments.base

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by duanlei on 2018/2/2.
 */
abstract class BaseDelegate : SwipeBackFragment() {

    abstract fun setLayout(): Any
    abstract fun onBindView(savedInstanceState: Bundle?, rootView: View)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val rootView = when (setLayout()) {
            is Int -> {
                inflater.inflate(setLayout() as Int, container, false)
            }
            is View -> {
                setLayout() as View
            }
            else -> {
                throw ClassCastException("setLayout() type must be int or View!")
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(savedInstanceState, view)
    }

    fun getProxyActivity(): FragmentActivity = _mActivity

    private val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            //取消注册，以避免内存泄露
            mCompositeSubscription.unsubscribe()
        }
        super.onDestroy()
    }

    open fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(subscriber))
    }
}