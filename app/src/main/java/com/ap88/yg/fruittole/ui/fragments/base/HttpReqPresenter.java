package com.ap88.yg.fruittole.ui.fragments.base;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by duanlei on 2018/4/10.
 *
 */
public class HttpReqPresenter implements IPresenter {

  private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

  public <M> void addSubscription(Observable<M> observable, Subscriber<M> subscriber) {
    mCompositeSubscription.add(
        observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber));
  }

  @Override
  public void start() {

  }

  @Override
  public void destroy() {
    if (mCompositeSubscription.hasSubscriptions()) {
      mCompositeSubscription.unsubscribe();
    }
  }
}
