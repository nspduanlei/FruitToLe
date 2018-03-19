package com.ap88.yg.fruittole.domain.model

/**
 * Created by duanlei on 2018/2/26.
 * 服务器接口实体
 */


/**
 * Result
 * Result<List<T>>
 * Result<ListPage<T>>
 */
data class Result<out T>(val data: T, val errorCode: String,
                         val errorMsg: String, val succeed: Boolean)

data class ListPage<out T>(val currentNo: Int, val pageCount: Int,
                                 val rows: List<T>, val totalElements: Int)


//每周上榜
data class WeeklyListBean(val imageUrl: String,
                          val text: String,
                          val sumWeight: Float,
                          val time: Int,
                          val name: String,
                          val userId: Long,
                          val userOrgId: Long,
                          val userType: String,
                          var listType: String)

//轮播图
data class BannerListBean(val content: String, val url: String)

data class MqInfoBean(val id: String,
                      val content: String,
                      val url: String)

data class AppleBean(val address: String,
                     val amount: Float,
                     val productTypeName: String, //供应 求购
                     val priceUnit: String,
                     val endAmount: Float,
                     val showCredateTime: String,
                     val skuName: String,
                     val firstImageUrl: String,
                     val weightUnit: String,
                     val weight: Float,
                     val viewNum: Int,
                     val showSecondInfo: List<String>,
                     val productTags: List<String>,
                     val id: String)

//果价竞猜
data class GuessInfo(val content: String,
                     val title: String,
                     val totalUser: Int,
                     val quotationHigh: Int,
                     val quotationLight: Int,
                     val id: String)


data class PreSearch(val keyword: String, val searchType: String)


