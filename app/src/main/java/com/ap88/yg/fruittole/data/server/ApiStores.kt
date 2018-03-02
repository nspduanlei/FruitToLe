package com.ap88.yg.fruittole.data.server

import com.ap88.yg.fruittole.domain.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by duanlei on 2018/2/26.
 *
 */
interface ApiStores {
    companion object {
        //baseUrl
        const val API_SERVER_URL = "https://yg.ap88.com/"
    }

    /**
     * ------------------首页接口----------------
     */
    //每周达人榜
    @POST("yg-voucher-service/ranking/queryRankingMan.apec")
    fun getWeeklyList(@Body requestBody: RequestBody): Observable<Result<List<WeeklyListBean>>>

    //获取轮播图
    @POST("yg-cms-service/cms/articleList.apec")
    fun getBannerList(@Body requestBody: RequestBody): Observable<Result<List<BannerListBean>>>

    //走马灯，果乐消息
    @POST("yg-society-service/societyPost/societyPostPage.apec")
    fun getMqMessageList(@Body requestBody: RequestBody): Observable<Result<ListPage<MqInfoBean>>>

    //果价猜猜猜
    @POST("yg-society-service/societyPost/newsQuo.apec")
    fun getGuessInfo(): Observable<Result<GuessInfo>>

    //供求信息
    @POST("_node_product/_all.apno")
    fun getSupplyReqList(@Body requestBody: RequestBody): Observable<Result<ListPage<AppleBean>>>

    /**
     * ------------------首页接口 end----------------
     */
}