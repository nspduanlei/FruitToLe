package com.ap88.yg.fruittole.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.text.Spanned
import android.util.SparseArray
import android.view.View
import android.widget.*
import com.ap88.yg.fruittole.utils.picasso.CircleTransform
import com.ap88.yg.fruittole.utils.picasso.RoundTransform
import com.squareup.picasso.Picasso

/**
 * Created by duanlei on 16/9/21.
 *
 */
class MyViewHolder(private val mConvertView: View, private val mContext: Context) :
        RecyclerView.ViewHolder(mConvertView) {

    private val mViews: SparseArray<View> = SparseArray()

    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(v: View)
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
    </T> */
    public fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)

        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    init {
        mConvertView.setOnClickListener { v ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(v)
            }
        }
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    fun setText(viewId: Int, text: String): MyViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setTextHint(viewId: Int, text: String): MyViewHolder {
        val tv = getView<TextView>(viewId)
        tv.hint = text
        return this
    }

    fun setTextSpanned(tv_stock_num: Int, spanned: Spanned): MyViewHolder {
        val tv = getView<TextView>(tv_stock_num)
        tv.text = spanned
        return this
    }

    fun setImageUrl(viewId: Int, url: String): MyViewHolder {
        val iv = getView<ImageView>(viewId)
        Picasso.with(mContext).load(url).into(iv)
        return this
    }

    fun setImageUrlRound(viewId: Int, url: String, radius: Int): MyViewHolder {
        val iv = getView<ImageView>(viewId)
        Picasso.with(mContext)
                .load(url)
                .transform(RoundTransform(mContext, radius))
                .into(iv)
        return this
    }

    fun setImageUrlCircle(viewId: Int, url: String): MyViewHolder {
        val iv = getView<ImageView>(viewId)
        Picasso.with(mContext)
                .load(url)
                .transform(CircleTransform(mContext))
                .into(iv)
        return this
    }


    fun setImageRes(viewId: Int, resId: Int): MyViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    fun setVisibility(viewId: Int, visibility: Int): MyViewHolder {
        val view = getView<View>(viewId)
        view.visibility = visibility
        return this
    }

    fun setOnClickLister(viewId: Int, listener: View.OnClickListener): MyViewHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, listener: View.OnLongClickListener): MyViewHolder {
        val view = getView<View>(viewId)
        view.setOnLongClickListener(listener)
        return this
    }

    fun setOnClickLister(viewId: Int, viewIdTwo: Int, listener: View.OnClickListener): MyViewHolder {
        val view = getView<View>(viewId)
        val viewTwo = getView<View>(viewIdTwo)
        view.setOnClickListener(listener)
        viewTwo.setOnClickListener(listener)
        return this
    }

    fun setBgColor(viewId: Int, color: String): MyViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(Color.parseColor(color))
        return this
    }

    fun setBgColor(viewId: Int, color: Int): MyViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setTextViewColor(viewId: Int, color: Int): MyViewHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(color)
        return this
    }

    fun setBgDrawable(viewId: Int, rId: Int): MyViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(rId)
        return this
    }

    fun setTextAndBg(viewId: Int, text: String, rId: Int): MyViewHolder {
        val view = getView<TextView>(viewId)
        view.text = text
        view.setBackgroundResource(rId)
        return this
    }

    fun setTextDrawable(viewId: Int, drawable: Drawable, index: Int): MyViewHolder {
        val textView = getView<TextView>(viewId)
        //    Drawable drawableRight = getResources().getDrawable(R.drawable.btn_follow);
        drawable.setBounds(0, 0, drawable.minimumWidth,
                drawable.minimumHeight)
        when (index) {
            1 -> textView.setCompoundDrawables(drawable, null, null, null)
            2 -> textView.setCompoundDrawables(null, drawable, null, null)
            3 -> textView.setCompoundDrawables(null, null, drawable, null)
            4 -> textView.setCompoundDrawables(null, null, null, drawable)
        }
        return this
    }

    fun setListView(lv_sku: Int, itemAdapter: BaseAdapter): MyViewHolder {
        val listView = getView<ListView>(lv_sku)
        listView.adapter = itemAdapter
        return this
    }

    fun setRbSelect(viewId: Int, isSelect: Boolean): MyViewHolder {
        val radioButton = getView<RadioButton>(viewId)
        radioButton.isChecked = isSelect
        return this
    }

    fun setRbListener(viewId: Int, listener: CompoundButton.OnCheckedChangeListener): MyViewHolder {
        val radioButton = getView<RadioButton>(viewId)
        radioButton.setOnCheckedChangeListener(listener)
        return this
    }

    fun setCbSelect(viewId: Int, isSelect: Boolean): MyViewHolder {
        val checkBox = getView<CheckBox>(viewId)
        checkBox.isChecked = isSelect
        return this
    }

    fun setCbListener(viewId: Int, listener: CompoundButton.OnCheckedChangeListener): MyViewHolder {
        val checkBox = getView<CheckBox>(viewId)
        checkBox.setOnCheckedChangeListener(listener)
        return this
    }

    fun setCbEnable(viewId: Int, isEnable: Boolean): MyViewHolder {
        val checkBox = getView<CheckBox>(viewId)
        checkBox.isEnabled = isEnable
        return this
    }

    fun setSwitchListener(viewId: Int, listener: CompoundButton.OnCheckedChangeListener): MyViewHolder {
        val switchCompat = getView<SwitchCompat>(viewId)
        switchCompat.setOnCheckedChangeListener(listener)
        return this
    }

    fun setSwitch(viewId: Int, isOpen: Boolean): MyViewHolder {
        val switchCompat = getView<SwitchCompat>(viewId)
        switchCompat.isChecked = isOpen
        return this
    }

    fun setSelected(viewId: Int, isSel: Boolean): MyViewHolder {
        val view = getView<View>(viewId)
        view.isSelected = isSel
        return this
    }

    fun setEnable(viewId: Int, b: Boolean): MyViewHolder {
        val view = getView<View>(viewId)
        view.isEnabled = b
        return this
    }


    fun setRvHorizontal(viewId: Int, context: Context, adapter: CommonRecyclerAdapter<*>): MyViewHolder {
        val recyclerView = getView<RecyclerView>(viewId)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        return this
    }

    fun setRvGv(viewId: Int, context: Context, colNum: Int, adapter: CommonRecyclerAdapter<*>): MyViewHolder {
        val recyclerView = getView<RecyclerView>(viewId)
        recyclerView.layoutManager = GridLayoutManager(context, colNum)
        recyclerView.adapter = adapter
        return this
    }

    fun setSeekBarListener(viewId: Int, listener: SeekBar.OnSeekBarChangeListener): MyViewHolder {
        val seekBar = getView<SeekBar>(viewId)
        seekBar.setOnSeekBarChangeListener(listener)
        return this
    }

    fun setViewRotation(viewId: Int, degress: Int): MyViewHolder {
        val view = getView<View>(viewId)
        view.rotation = degress.toFloat()
        return this
    }


}
