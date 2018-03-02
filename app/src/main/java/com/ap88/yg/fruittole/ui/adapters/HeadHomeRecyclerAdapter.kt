package com.ap88.yg.fruittole.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.ui.fragments.main.home.HeadViewHolder

/**
 * Created by duanlei on 16/9/21.
 *
 */
abstract class HeadHomeRecyclerAdapter<T>(private val mContext: Context,
                                          private val mResId: Int,
                                          private val mData: MutableList<T?>) :
        CommonRecyclerAdapter<T>(mContext, mResId, mData) {

    //顶部
    private val mTypeItemHead = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return if (viewType == mTypeItemHead) {
            val view = mLayoutInflater.inflate(R.layout.layout_home_head, parent, false)
            HeadViewHolder(view)
        } else super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeadViewHolder -> convertHead(holder, position)
            is MyViewHolder -> convert(holder, mData[position - 1]!!, position)
            else -> super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> mTypeItemHead
            mData[position-1] == null -> mTypeLoading
            else -> mTypeItem
        }
    }
    abstract fun convertHead(holder: HeadViewHolder, position: Int)


    override fun insertedLoading() {
        mData.add(null)
        notifyItemInserted(mData.size)
    }

    override fun removeLoading() {
        if (mData.size > 1) {
            mData.removeAt(mData.size - 1)
            notifyItemRemoved(mData.size + 1)
        }
    }



}
