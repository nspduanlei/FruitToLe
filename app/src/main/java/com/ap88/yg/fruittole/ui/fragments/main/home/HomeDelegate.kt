package com.ap88.yg.fruittole.ui.fragments.main.home

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.data.server.ApiCallback
import com.ap88.yg.fruittole.data.server.ApiClient
import com.ap88.yg.fruittole.data.server.ApiStores
import com.ap88.yg.fruittole.domain.model.*
import com.ap88.yg.fruittole.ui.adapters.CommonRecyclerAdapter
import com.ap88.yg.fruittole.ui.adapters.MyViewHolder
import com.ap88.yg.fruittole.ui.fragments.BottomDelegate
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.bottom.BottomItemDelegate
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl
import com.ap88.yg.fruittole.ui.utils.AliYun
import com.ap88.yg.fruittole.ui.utils.DialogUtils
import com.ap88.yg.fruittole.ui.utils.LoginUtils
import com.ap88.yg.fruittole.ui.widget.FlowLayout
import com.ap88.yg.fruittole.utils.RequestUtils
import com.ap88.yg.fruittole.utils.model.MenuEntity
import com.bigkoo.convenientbanner.ConvenientBanner
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.delegate_home.*
import kotlinx.android.synthetic.main.layout_home_head.view.*
import org.jetbrains.anko.toast
import java.util.*


/**
 * Created by duanlei on 2018/2/1.
 *
 */
class HomeDelegate : BottomItemDelegate() {

    private lateinit var mAdapter: CommonRecyclerAdapter<AppleBean>

    override fun setLayout() = R.layout.delegate_home

    //private var mIsRefresh = false

//    private lateinit var mLoadMoreHelp: LoadMoreHelp

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        //设置滑动列表头部
        initList()

        //设置导航条的颜色渐变
        setScroll()

        //获取网络数据
        getData()

        bindClick()
    }

    private fun bindClick() {
        tv_sel_location.setOnClickListener {
            this.getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/province?sign=home"))
        }

        tv_search.setOnClickListener {
            this.getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/search?search=" +
                    tv_search.text))
        }

        fl_msg.setOnClickListener {
            if (LoginUtils.checkLogin(this.getParentDelegate())) {
                this.getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/messageInfo"))
            }
        }
    }

    /**
     * 获取数据
     */
    private fun getData() {
        getListData()
        getPreSearchInfo()
    }

    private fun getHeadData() {
        getWeeklyListData(rankingTypeF)
        getWeeklyListData(rankingTypeA)
        getBannerListData()
        getMqMessageData()
        getGuessInfoData()
    }

    var totalDy = 0

    private fun setScroll() {
        xrv_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                //Log.e("test", "totalDy------------$totalDy")

                totalDy += dy
                val toolbarHeight =
                        (header.convenientBanner.height -
                                ll_title.height).toFloat()

                if (totalDy <= toolbarHeight) {
                    val scale = totalDy / toolbarHeight
                    val alpha = scale * 255
                    ll_title.setBackgroundColor(Color.argb(alpha.toInt(),
                            235, 88, 82))
                } else {
                    ll_title.setBackgroundResource(R.color.colorMain)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            }
        })

    }

    private var mIsEnd = false

    /**
     * 页面list初始化
     */
    private fun initList() {
        xrv_home.layoutManager = LinearLayoutManager(context)

        xrv_home.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin)

        xrv_home.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                mIsEnd = false
                totalDy = 0
//                mIsRefresh = true
                if (mWeeklyListAdapter != null) {
                    mWeeklyListAdapter!!.clear()
                }
                mCurPageNum = 1

                getData()
                getHeadData()
            }

            override fun onLoadMore() {
                if (mIsEnd) {
                    xrv_home.loadMoreComplete()
                    return
                }
                mCurPageNum++
                getListData()
            }
        })

        initHeader()

        xrv_home.addHeaderView(header)

        mAdapter = object : CommonRecyclerAdapter<AppleBean>(activity!!,
                R.layout.item_home_apple_info, mutableListOf()) {

            override fun convert(holder: MyViewHolder, t: AppleBean, position: Int) {
                holder.setImageUrlRound(R.id.iv_apple_image, t.firstImageUrl + AliYun.HEAD_SUFFIX, 2)
                        .setText(R.id.tv_title, t.skuName)
                        .setText(R.id.tv_tag, t.productTypeName)
                        .setText(R.id.tv_price_unit, t.priceUnit)
                        .setText(R.id.tv_count, String.format("%.0f %s", t.weight, t.weightUnit))
                        .setText(R.id.tv_address, t.address)
                        .setText(R.id.tv_look_count, String.format("%d人浏览", t.viewNum))
                        .setText(R.id.tv_time, t.showCredateTime)

                val tagsList: MutableList<ProductTag> = mutableListOf()


                t.productTags.mapTo(tagsList) {
                    ProductTag(it.tagName, "#ffffff",
                            R.drawable.drawable_tag_apple_info_y)
                }
                t.showSecondInfo.mapTo(tagsList) {
                    ProductTag(it, "#747474",
                            R.drawable.drawable_tag_apple_info_type)
                }

                //标签处理
                val flTags = holder.getView<FlowLayout>(R.id.fl_tags)
                flTags.removeAllViews()
                flTags.setFlowLayoutByType(tagsList)


                if (t.productTypeName == "供应") {
                    holder.setBgDrawable(R.id.tv_tag, R.drawable.drawable_tag_apple_info)
                            .setText(R.id.tv_price, t.amount.toString())
                } else if (t.productTypeName == "求购") {
                    holder.setBgDrawable(R.id.tv_tag, R.drawable.drawable_tag_apple_info_type_req)
                            .setText(R.id.tv_price, String.format("%.1f~%.1f", t.amount, t.endAmount))
                }

                //item点击跳转
                holder.setOnItemClickListener(object : MyViewHolder.OnItemClickListener {
                    override fun onItemClick(v: View) {
                        if (data != null && data!![holder.adapterPosition] != null) {

                            getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(
                                    ApiStores.URL_WEB + "#/detail?id=" +
                                            data!![holder.adapterPosition]!!.id))
                        }

                    }
                })
            }
        }
        xrv_home.adapter = mAdapter
    }

    private lateinit var header: View

    private fun initHeader() {
        header = LayoutInflater.from(context).inflate(R.layout.layout_home_head, null, false)
        //设置菜单
        header.rv_menu.layoutManager = GridLayoutManager(context, 4)
        val items: MutableList<MenuEntity?> = mutableListOf()
        items.add(MenuEntity(R.drawable.btn_home_menu_market, "市场行情"))
        items.add(MenuEntity(R.drawable.btn_home_menu_sale, "求购信息"))
        items.add(MenuEntity(R.drawable.btn_home_menu_supply, "供应信息"))
        items.add(MenuEntity(R.drawable.btn_home_menu_circle, "果圈"))
        header.rv_menu.adapter = object : CommonRecyclerAdapter<MenuEntity>(activity!!,
                R.layout.item_menu, items) {
            override fun convert(holder: MyViewHolder, t: MenuEntity, position: Int) {
                holder.setImageRes(R.id.iv_icon_menu, t.resId)
                        .setText(R.id.tv_title, t.title)
                        .setOnItemClickListener(object : MyViewHolder.OnItemClickListener {
                            override fun onItemClick(v: View) {
                                when (holder.adapterPosition) {
                                    0 -> getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/news"))
                                    1 -> getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/gq?gq=QGTYPE"))
                                    2 -> getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB + "#/gq?gq=GYTYPE"))
                                    3 -> {
                                        val bottomDelegate = getParentDelegate<BaseDelegate>() as BottomDelegate
                                        bottomDelegate.setCurItem(3)
                                    }
                                }
                            }
                        })
            }
        }

        header.ll_guess.setOnClickListener {
            if (mGuessInfo != null) {
                getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(
                        ApiStores.URL_WEB + "#/priveMovement?useId=" +
                                mGuessInfo!!.id))
            }
        }

        header.btn_sign.setOnClickListener {
            if (LoginUtils.isLogin()) {
                //签到
                getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(
                        ApiStores.URL_WEB + "#/signIN"))
            } else {
                DialogUtils.showLoginAlertDiaLog(getParentDelegate())
            }
        }

        //初始化每周上榜
        initWeeklyList(header.rv_expert)
        //初始化轮播图
        initBanners()
        getHeadData()
    }


    private val mBannerImages: MutableList<BannerListBean> = mutableListOf()

    private fun initBanners() {
        val banner = header.convenientBanner as ConvenientBanner<BannerListBean>
        banner.setPages(
                { BannerImageHolderView() }, mBannerImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(intArrayOf(R.drawable.shape_page_indicator, R.drawable.shape_page_indicator_focused))
                //设置指示器的方向
                .startTurning(10000) //4s自动滚动
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener {
                    getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(mBannerImages[it].url))
                }.scrollDuration = 1000
    }

    private var marqueeViews: MutableList<View> = ArrayList()

    //适配器
    private var mWeeklyListAdapter: CommonRecyclerAdapter<WeeklyListBean>? = null

    private fun initWeeklyList(rvExpert: RecyclerView) {
        rvExpert.layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
        mWeeklyListAdapter = object : CommonRecyclerAdapter<WeeklyListBean>(activity!!,
                R.layout.item_weekly_list, mutableListOf()) {
            override fun convert(holder: MyViewHolder, t: WeeklyListBean, position: Int) {
                holder.setText(R.id.tv_user_name, t.name)
                        .setText(R.id.tv_user_tag, t.userType)
                        .setImageUrlCircle(R.id.tv_user_img, t.imageUrl + AliYun.HEAD_SUFFIX)

                if (t.listType == rankingTypeF) {
                    holder.setText(R.id.tv_list_type, String.format(" · %s", "调果达人"))
                            .setText(R.id.tv_public_count, String.format("累计调果%.0f斤", t.sumWeight))
                } else if (t.listType == rankingTypeA) { //活跃达人
                    holder.setText(R.id.tv_list_type, String.format(" · %s", "活跃达人"))
                            .setText(R.id.tv_public_count, String.format("累计发布供求%d条", t.time))
                }
            }
        }
        rvExpert.adapter = mWeeklyListAdapter
    }

    /**
     * rankingType: FRUITING_PEOPLE 调果达人， ACTIVE_MAN 活跃达人
     */
    private val rankingTypeF = "FRUITING_PEOPLE"
    private val rankingTypeA = "ACTIVE_MAN"

    private fun getWeeklyListData(rankingType: String) {
        val params: MutableMap<String, String> = mutableMapOf()
        params["rankingType"] = rankingType

        addSubscription(ApiClient.retrofit().getWeeklyList(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<List<WeeklyListBean>>>() {
                    override fun onSuccess(t: Result<List<WeeklyListBean>>) {
                        //每周上榜填充类型字段
                        for (item in t.data) {
                            item.listType = rankingType
                        }

                        mWeeklyListAdapter!!.addAll(t.data)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    /**
     * 获取轮播图
     */
    private fun getBannerListData() {
        val params: MutableMap<String, String> = mutableMapOf()
        params["category"] = "HOMEPAGE"
        params["channelCode"] = "HOME_BARNNER"

        addSubscription(ApiClient.retrofit().getBannerList(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<List<BannerListBean>>>() {
                    override fun onSuccess(t: Result<List<BannerListBean>>) {
                        mBannerImages.clear()
                        mBannerImages.addAll(t.data)
                        header.convenientBanner.notifyDataSetChanged()
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    private fun getMqMessageData() {
        val params: MutableMap<String, Any> = mutableMapOf()
        params["auditState"] = "Y"
        params["pageSize"] = 10
        params["pageNumber"] = 1
        params["realm"] = "ARTICLE"

        addSubscription(ApiClient.retrofit().getMqMessageList(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<ListPage<MqInfoBean>>>() {
                    override fun onSuccess(t: Result<ListPage<MqInfoBean>>) {
                        fillMqMessageData(t.data.rows)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }


    private fun fillMqMessageData(mqMessage: List<MqInfoBean>) {
        marqueeViews.clear()

        var moreView: LinearLayout?
        var tvInfo1: TextView?
        var tvInfo2: TextView? = null

        for (i in mqMessage.indices) {
            if (i % 2 == 0) {
                moreView = LayoutInflater.from(activity)
                        .inflate(R.layout.layout_mq_message, null) as LinearLayout

                //初始化布局的控件
                tvInfo1 = moreView.findViewById(R.id.tv_content_1) as TextView
                tvInfo2 = moreView.findViewById(R.id.tv_content_2) as TextView

                marqueeViews.add(moreView)

                tvInfo1.text = mqMessage[i].content


                tvInfo1.setOnClickListener {
                    getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB +
                            "#/newsDetail?id=" + mqMessage[i].id))
                }
            }
            if (i % 2 == 1) {
                tvInfo2!!.text = mqMessage[i].content

                tvInfo2.setOnClickListener {
                    getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB +
                            "#/newsDetail?id=" + mqMessage[i].id))
                }
            }
        }
        header.marquee_view.setViews(marqueeViews)
    }

    var mGuessInfo: GuessInfo? = null

    private fun getGuessInfoData() {
        addSubscription(ApiClient.retrofit().getGuessInfo(),
                object : ApiCallback<Result<GuessInfo>>() {
                    override fun onSuccess(t: Result<GuessInfo>) {

                        mGuessInfo = t.data
                        if (mGuessInfo == null) {
                            header.ll_guess.visibility = View.GONE
                            return
                        } else {
                            header.ll_guess.visibility = View.VISIBLE
                        }

                        header.tv_guess_count.text = t.data.totalUser.toString()
                        header.tv_guess_title.text = String.format("%s(%s)",
                                t.data.title, t.data.content)

                        val pLeft: Int = if (t.data.totalUser == 0) {
                            50
                        } else {
                            ((t.data.quotationHigh.toFloat() / t.data.totalUser.toFloat()) * 100).toInt()
                        }
                        header.guessView.setProgress(pLeft)
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

    var mCurPageNum: Int = 1

    private fun getListData() {
        val params: MutableMap<String, Any> = mutableMapOf()
        params["keyWord"] = ""
        params["orderType"] = "DATEDES"
        params["pageSize"] = ApiStores.PAGE_SIZE
        params["pageNumber"] = mCurPageNum
        params["searchType"] = ""
        addSubscription(ApiClient.retrofit().getSupplyReqList(RequestUtils.getRequestBodyJson(params)),
                object : ApiCallback<Result<ListPage<AppleBean>>>() {
                    override fun onSuccess(t: Result<ListPage<AppleBean>>) {
                        Log.e("test002", "onSuccess------mCurPageNum----" + mCurPageNum)
                        Log.e("test002", "onSuccess------size----" + t.data.rows.size)

                        if (mCurPageNum == 1) { //刷新
                            mAdapter.addAllC(t.data.rows)
                            xrv_home.refreshComplete()
                        } else {
                            if (t.data.rows.size < ApiStores.PAGE_SIZE) {
                                if (activity != null) {
                                    activity!!.toast("没有更多记录")
                                }
                            }
                            mAdapter.addAll(t.data.rows)
                            xrv_home.loadMoreComplete()
                        }

                        if (t.data.rows.size < ApiStores.PAGE_SIZE) {
                            mIsEnd = true
                        }
                    }

                    override fun onFailure(msg: String?) {
                        Log.e("test002", "onFailure----------")
                    }

                    override fun onFinish() {
                    }
                })
    }

    private fun getPreSearchInfo() {
        val params: MutableMap<String, Any> = mutableMapOf()
        params["searchType"] = "YZSS"
        addSubscription(ApiClient.retrofit().getPreSearch(RequestUtils.getRequestBody(params)),
                object : ApiCallback<Result<PreSearch>>() {
                    override fun onSuccess(t: Result<PreSearch>) {
                        Log.e("test001", "keyword----------" + t.data.keyword)
                        tv_search.text = t.data.keyword
                    }

                    override fun onFailure(msg: String?) {
                    }

                    override fun onFinish() {
                    }
                })
    }

}