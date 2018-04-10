package com.ap88.yg.fruittole.ui.fragments.main.home

import com.ap88.yg.fruittole.data.server.ApiCallback
import com.ap88.yg.fruittole.data.server.ApiClient
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.domain.model.*
import com.ap88.yg.fruittole.ui.fragments.base.HttpReqPresenter
import com.ap88.yg.fruittole.utils.RequestUtils

/**
 * Created by duanlei on 2018/4/10.
 *
 */
class HomePresenter internal constructor(private var mView: IHome.View) : HttpReqPresenter(),
        IHome.Presenter {

    override fun getWeeklyListData(rankingType: String) {
        val params: MutableMap<String, String> = mutableMapOf()
        params["rankingType"] = rankingType

        addSubscription(ApiClient.retrofit().getWeeklyList(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<List<WeeklyListBean>>>() {
                    override fun onSuccess(t: Result<List<WeeklyListBean>>) {
                        mView.getWeeklyListDataSuc(t.data, rankingType)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    override fun getBannerListData() {
        val params: MutableMap<String, String> = mutableMapOf()
        params["category"] = "HOMEPAGE"
        params["channelCode"] = "HOME_BARNNER"

        addSubscription(ApiClient.retrofit().getBannerList(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<List<BannerListBean>>>() {
                    override fun onSuccess(t: Result<List<BannerListBean>>) {
                      mView.getBannerListDataSuc(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })

    }

    override fun getMqMessageData() {
        val params: MutableMap<String, Any> = mutableMapOf()
        params["auditState"] = "Y"
        params["pageSize"] = 10
        params["pageNumber"] = 1
        params["realm"] = "ARTICLE"

        addSubscription(ApiClient.retrofit().getMqMessageList(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<ListPage<MqInfoBean>>>() {
                    override fun onSuccess(t: Result<ListPage<MqInfoBean>>) {
                        mView.getMqMessageDataSuc(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    override fun getGuessInfoData() {
        addSubscription(ApiClient.retrofit().getGuessInfo(),
                object : ApiCallback<Result<GuessInfo>>() {
                    override fun onSuccess(t: Result<GuessInfo>) {
                        mView.getGuessInfoDataSuc(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    override fun getListData(curPageNum: Int) {
        val params: MutableMap<String, Any> = mutableMapOf()
        params["keyWord"] = ""
        params["orderType"] = "DATEDES"
        params["pageSize"] = ApiStores.PAGE_SIZE
        params["pageNumber"] = curPageNum
        params["searchType"] = ""
        addSubscription(ApiClient.retrofit().getSupplyReqList(RequestUtils.getRequestBodyJson(params)),
                object : ApiCallback<Result<ListPage<AppleBean>>>() {
                    override fun onSuccess(t: Result<ListPage<AppleBean>>) {
                        mView.getListDataSuc(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    override fun getPreSearchInfo() {
        val params: MutableMap<String, Any> = mutableMapOf()
        params["searchType"] = "YZSS"
        addSubscription(ApiClient.retrofit().getPreSearch(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<PreSearch>>() {
                    override fun onSuccess(t: Result<PreSearch>) {
                        mView.getPreSearchInfoSuc(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    override fun checkSign() {
        addSubscription(ApiClient.retrofit().checkSign(),
                object : ApiCallback<Result<CheckSign>>() {
                    override fun onSuccess(t: Result<CheckSign>) {
                        mView.checkSignBack(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }
}
