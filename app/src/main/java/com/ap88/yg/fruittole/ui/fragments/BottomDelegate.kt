package com.ap88.yg.fruittole.ui.fragments

import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.domain.model.BottomTabBean
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import com.ap88.yg.fruittole.ui.fragments.bottom.BaseBottomDelegate
import com.ap88.yg.fruittole.ui.fragments.main.action.ActionDelegate
import com.ap88.yg.fruittole.ui.fragments.main.circle.CircleDelegate
import com.ap88.yg.fruittole.ui.fragments.main.home.HomeDelegate
import com.ap88.yg.fruittole.ui.fragments.main.me.MeDelegate
import com.ap88.yg.fruittole.ui.fragments.main.source.SourceDelegate

/**
 * Created by duanlei on 2018/2/2.
 */

class BottomDelegate: BaseBottomDelegate() {
    override fun setItems():
            LinkedHashMap<BottomTabBean, BaseDelegate> {
        val items = LinkedHashMap<BottomTabBean, BaseDelegate>()
        items[BottomTabBean(R.drawable.drawable_btn_bottom_bar_home, "首页")] = HomeDelegate()
        items[BottomTabBean(R.drawable.drawable_btn_bottom_bar_source, "货源")] =
                SourceDelegate()
        items[BottomTabBean(R.drawable.btn_bot_bar_add, "")] = ActionDelegate()
        items[BottomTabBean(R.drawable.drawable_btn_bottom_bar_cricle, "果圈")] =
                CircleDelegate()
        items[BottomTabBean(R.drawable.drawable_btn_bottom_bar_me, "我的")] =
                MeDelegate()

        return items
    }

    override fun setIndex(): Int {
        return 0
    }

}
