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
import com.ap88.yg.fruittole.utils.model.MenuEntity
import com.bigkoo.convenientbanner.ConvenientBanner
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.delegate_home.*
import kotlinx.android.synthetic.main.layout_home_head.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import java.util.*


/**
 * Created by duanlei on 2018/2/1.
 *
 */
class HomeDelegate : BottomItemDelegate(), IHome.View {

    private lateinit var mAdapter: CommonRecyclerAdapter<AppleBean>
    override fun setLayout() = R.layout.delegate_home

    private lateinit var mPresenter: IHome.Presenter

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

        mPresenter = HomePresenter(this)

        //设置滑动列表头部
        initList()

        //设置导航条的颜色渐变
        setScroll()

        //获取网络数据
        getData()

        bindClick()

        EventBus.getDefault().register(this)
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
        mPresenter.getListData(mCurPageNum)
        mPresenter.getPreSearchInfo()
    }

    private fun getHeadData() {
        mPresenter.getWeeklyListData(rankingTypeF)
        mPresenter.getWeeklyListData(rankingTypeA)
        mPresenter.getBannerListData()
        mPresenter.getMqMessageData()
        mPresenter.getGuessInfoData()
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

    private fun doRefresh() {
        mIsEnd = false
        totalDy = 0
        if (mWeeklyListAdapter != null) {
            mWeeklyListAdapter!!.clear()
        }
        mCurPageNum = 1

        getData()
        getHeadData()
    }

    /**
     * 页面list初始化
     */
    private fun initList() {
        xrv_home.layoutManager = LinearLayoutManager(context)
        xrv_home.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin)
        xrv_home.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                doRefresh()
            }

            override fun onLoadMore() {
                if (mIsEnd) {
                    xrv_home.loadMoreComplete()
                    return
                }
                mCurPageNum++
                mPresenter.getListData(mCurPageNum)
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
                            .setText(R.id.tv_price, String.format("%.1f~%.1f", t.startAmount,
                                    t.endAmount))
                }

                //item点击跳转
                holder.setOnItemClickListener(object : MyViewHolder.OnItemClickListener {
                    override fun onItemClick(v: View) {

                        Log.e("test009", "holder.adapterPosition---------" +
                                holder.adapterPosition)

                        if (data != null && data!![holder.adapterPosition - 2] != null) {
                            getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(
                                    ApiStores.URL_WEB + "#/detail?id=" +
                                            data!![holder.adapterPosition - 2]!!.id))
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

        //果价猜猜猜
        header.ll_guess.setOnClickListener {
            if (mGuessInfo != null) {
                getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(
                        ApiStores.URL_WEB + "#/priveMovement?useId=" +
                                mGuessInfo!!.id))
            }
        }

        //签到
        header.btn_sign.setOnClickListener {
            if (LoginUtils.isLogin()) {
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

                tvInfo1.text = mqMessage[i].title


                tvInfo1.setOnClickListener {
                    getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB +
                            "#/newsDetail?id=" + mqMessage[i].id))
                }
            }
            if (i % 2 == 1) {
                tvInfo2!!.text = mqMessage[i].title

                tvInfo2.setOnClickListener {
                    getParentDelegate<BaseDelegate>().start(WebDelegateImpl.create(ApiStores.URL_WEB +
                            "#/newsDetail?id=" + mqMessage[i].id))
                }
            }
        }
        header.marquee_view.setViews(marqueeViews)
    }

    private var mGuessInfo: GuessInfo? = null

    var mCurPageNum: Int = 1

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onMessageEvent(event: MessageEvent) {
        if (event.id == MessageEvent.HOME_UPDATE) {
            doRefresh()
        } else if (event.id == MessageEvent.CITY_SELECT) {
            tv_sel_location.text = LoginUtils.cityName
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (header == null) {
            return
        }
        if (hidden) {
            header.convenientBanner.stopTurning()
            header.marquee_view.stopFlipping()
        } else {
            header.convenientBanner.startTurning(10000)
            header.marquee_view.startFlipping()
        }
    }

    /**
     * ----------------网络请求返回处理---------------------
     */
    override fun getWeeklyListDataSuc(data: MutableList<WeeklyListBean>, rankingType: String) {
        //每周上榜填充类型字段
        for (item in data) {
            item.listType = rankingType
        }

        mWeeklyListAdapter!!.addAll(data)
    }

    override fun getBannerListDataSuc(data: MutableList<BannerListBean>) {
        mBannerImages.clear()
        mBannerImages.addAll(data)
        header.convenientBanner.notifyDataSetChanged()
    }

    override fun getMqMessageDataSuc(data: ListPage<MqInfoBean>) {
        fillMqMessageData(data.rows)
    }

    override fun getGuessInfoDataSuc(data: GuessInfo) {
        mGuessInfo = data
        if (mGuessInfo == null) {
            header.ll_guess.visibility = View.GONE
            return
        } else {
            header.ll_guess.visibility = View.VISIBLE
        }

        header.tv_guess_count.text = data.totalUser.toString()
        header.tv_guess_title.text = String.format("%s(%s)",
                data.title, data.content)

        val pLeft: Int = if (data.totalUser == 0) {
            50
        } else {
            ((data.quotationHigh.toFloat() / data.totalUser.toFloat()) * 100).toInt()
        }
        header.guessView.setProgress(pLeft)
    }

    override fun getListDataSuc(data: ListPage<AppleBean>) {
        if (mCurPageNum == 1) { //刷新
            mAdapter.addAllC(data.rows)
            xrv_home.refreshComplete()
        } else {
            if (data.rows.size < ApiStores.PAGE_SIZE) {
                if (activity != null) {
                    activity!!.toast("没有更多记录")
                }
            }
            mAdapter.addAll(data.rows)
            xrv_home.loadMoreComplete()
        }

        if (data.rows.size < ApiStores.PAGE_SIZE) {
            mIsEnd = true
        }
    }

    override fun getPreSearchInfoSuc(data: PreSearch) {
        tv_search.text = data.keyword
    }

    override fun checkSignBack(data: CheckSign?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroy()
        EventBus.getDefault().unregister(this)
    }
}