package com.ap88.yg.fruittole.ui.fragments.base;

/**
 * Created by duanlei on 2018/4/10.
 */

public interface IPresenter {
  /**
   * 调用该方法表示presenter被激活了
   */
  void start();

  /**
   * 调用此方法表示presenter要结束了
   * 其目的是为了接触相互持有导致的内存泄漏
   */
  void destroy();

}
