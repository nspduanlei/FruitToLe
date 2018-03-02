package com.ap88.yg.fruittole.ui.fragments.main.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ap88.yg.fruittole.ui.widget.MarqueeView
import com.ap88.yg.fruittole.ui.widget.MyProgressView
import com.bigkoo.convenientbanner.ConvenientBanner
import kotlinx.android.synthetic.main.layout_home_head.view.*

/**
 * Created by duanlei on 2018/2/24.
 */
class HeadViewHolder(mConvertView: View) :
        RecyclerView.ViewHolder(mConvertView) {

    var rvMenu: RecyclerView = mConvertView.rv_menu
    var marquee_view: MarqueeView = mConvertView.marquee_view
    var rvExpert: RecyclerView = mConvertView.rv_expert
    var convenientBanner: ConvenientBanner<*> = mConvertView.convenientBanner

    var guessTitle: TextView = mConvertView.tv_guess_title
    val guessCount: TextView = mConvertView.tv_guess_count
    val guessPro: MyProgressView = mConvertView.guessView
}