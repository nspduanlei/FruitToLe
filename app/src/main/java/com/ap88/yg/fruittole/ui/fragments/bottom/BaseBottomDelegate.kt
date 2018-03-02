package com.ap88.yg.fruittole.ui.fragments.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.domain.model.BottomTabBean
import com.ap88.yg.fruittole.ui.fragments.base.BaseDelegate
import kotlinx.android.synthetic.main.delegate_base_bottom.*
import kotlinx.android.synthetic.main.layout_bottom_item_icon_text.view.*
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by duanlei on 2018/2/2.
 *
 */
abstract class BaseBottomDelegate: BaseDelegate(), View.OnClickListener {

    private val tabBeans = ArrayList<BottomTabBean>()
    private val itemFragments = ArrayList<BaseDelegate>()
    private val items = LinkedHashMap<BottomTabBean, BaseDelegate>()

    private var mCurrent = 0
    private var mIndex = 0
    private var mClickedColor = Color.RED
    private var mDefaultColor = Color.CYAN

    abstract fun setIndex(): Int

//    @ColorInt
//    abstract fun setClickColor(): Int

    abstract fun setItems(): LinkedHashMap<BottomTabBean, BaseDelegate>

    override fun setLayout() = R.layout.delegate_base_bottom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获取索引
        mIndex = setIndex()

//        //获取选中颜色
//        if (setClickColor() != 0) {
//            mClickedColor = setClickColor()
//        }

        mClickedColor = Color.parseColor("#55565a")
        mDefaultColor = Color.parseColor("#afafaf")

        //获取fragments
        items.putAll(setItems())

        //获取图片和标题列表
        for (item in items.entries) {
            tabBeans.add(item.key)
            itemFragments.add(item.value)
        }
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val size = items.size
        for (i in 0 until size) {
            LayoutInflater.from(context)
                    .inflate(R.layout.layout_bottom_item_icon_text, llBottomBar)

            val item = llBottomBar.getChildAt(i)

            item.tag = i
            item.setOnClickListener(this)

            val itemIcon = item.ivIcon
            val itemTitle = item.tvTitle

            val bean = tabBeans[i]
            itemIcon.setImageResource(bean.icon)
            itemTitle.text = bean.title

            if (i == mIndex) {
                itemIcon.isSelected = true
                itemTitle.setTextColor(mClickedColor)
            }
        }

        val delegateArray: Array<SupportFragment> =
                itemFragments.toArray(arrayOf<SupportFragment>())


        loadMultipleRootFragment(R.id.cflContainer, mIndex,
                *delegateArray)

    }

    override fun onClick(v: View) {
        val tag = v.tag as Int
        resetColor()

        v.ivIcon.isSelected = true
        v.tvTitle.setTextColor(mClickedColor)

        showHideFragment(itemFragments[tag], itemFragments[mCurrent])

        mCurrent = tag
    }

    private fun resetColor() {
        val count = llBottomBar.childCount
        for (i in 0 until count) {
            val item = llBottomBar.getChildAt(i)
            val itemIcon = item.ivIcon
            val itemTitle = item.tvTitle

            itemIcon.isSelected = false
            itemTitle.setTextColor(mDefaultColor)
        }
    }
}
