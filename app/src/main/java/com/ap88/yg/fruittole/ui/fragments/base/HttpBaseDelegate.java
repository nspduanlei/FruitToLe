package com.ap88.yg.fruittole.ui.fragments.base;

/**
 * Created by duanlei on 2018/4/10.
 *
 */
public abstract class HttpBaseDelegate extends BaseDelegate {

  private HttpReqPresenter mPresenter = new HttpReqPresenter();

  @Override
  public void onDestroy() {
    mPresenter.cancelRequests();
    super.onDestroy();
  }
}
