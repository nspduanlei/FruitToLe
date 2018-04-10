package com.ap88.yg.fruittole.ui.fragments.main.home;

import com.ap88.yg.fruittole.domain.model.AppleBean;
import com.ap88.yg.fruittole.domain.model.BannerListBean;
import com.ap88.yg.fruittole.domain.model.CheckSign;
import com.ap88.yg.fruittole.domain.model.GuessInfo;
import com.ap88.yg.fruittole.domain.model.ListPage;
import com.ap88.yg.fruittole.domain.model.MqInfoBean;
import com.ap88.yg.fruittole.domain.model.PreSearch;
import com.ap88.yg.fruittole.domain.model.WeeklyListBean;
import com.ap88.yg.fruittole.ui.fragments.base.IPresenter;

import java.util.List;

/**
 * Created by duanlei on 2018/4/10.
 */

public interface IHome {

  interface View {
    void getWeeklyListDataSuc(List<WeeklyListBean> data, String rankingType);
    void getBannerListDataSuc(List<BannerListBean> data);
    void getMqMessageDataSuc(ListPage<MqInfoBean> data);
    void getGuessInfoDataSuc(GuessInfo data);
    void getListDataSuc(ListPage<AppleBean> data);
    void getPreSearchInfoSuc(PreSearch data);
    void checkSignBack(CheckSign data);
  }

  interface Presenter extends IPresenter {
    /**
     * 获取每周达人榜数据
     */
    void getWeeklyListData(String rankingType);

    /**
     * 获取轮播图数据
     */
    void getBannerListData();

    /**
     * 获取跑马灯数据
     */
    void getMqMessageData();

    /**
     * 获取果价猜猜猜数据
     */
    void getGuessInfoData();

    /**
     * 获取供求列表数据
     */
    void getListData(int curPageNum);

    /**
     * 获取搜索关键字
     */
    void getPreSearchInfo();

    /**
     * 检查是否签到
     */
    void checkSign();
  }

}
