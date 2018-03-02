package com.ap88.yg.fruittole.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.ap88.yg.fruittole.R

/**
 * Created by duanlei on 16/9/21.
 *
 */
abstract class CommonRecyclerAdapter<T>(private val mContext: Context,
                                        private val mResId: Int,
                                        private val mData: MutableList<T?>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    //普通item
    val mTypeItem = 0
    //底部加载更多
    val mTypeLoading = 1

    val data: List<T?>?
        get() = mData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == mTypeItem) {
            val view = mLayoutInflater.inflate(mResId, parent, false)
            return MyViewHolder(view, mContext)
        } else if (viewType == mTypeLoading) {
            val view = mLayoutInflater.inflate(R.layout.layout_load_more, parent, false)
            return LoadingViewHolder(view)
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            convert(holder, mData[position]!!, position)
        } else if (holder is CommonRecyclerAdapter<*>.LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData[position] == null) mTypeLoading else mTypeItem
    }

    abstract fun convert(holder: MyViewHolder, t: T, position: Int)

    fun clear() {
        mData.clear()
    }

    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }

    fun addAll(data: List<T>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun addAllC(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    open fun insertedLoading() {
        mData.add(null)
        notifyItemInserted(mData.size - 1)
    }

    open fun removeLoading() {
        if (mData.size > 1) {
            mData.removeAt(mData.size - 1)
            notifyItemRemoved(mData.size)
        }
    }

    private inner class LoadingViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        internal var progressBar: ProgressBar =
                itemView.findViewById<View>(R.id.progressBar) as ProgressBar
    }
}
