package com.ap88.yg.fruittole.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by duanlei on 16/9/21.
 *
 */
abstract class CommonRecyclerAdapter<T>(private val mContext: Context,
                                        private val mResId: Int,
                                        private val mData: MutableList<T?>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    val data: List<T?>?
        get() = mData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val view = mLayoutInflater.inflate(mResId, parent, false)
        return MyViewHolder(view, mContext)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        convert(holder as MyViewHolder, mData[position]!!, position)
    }

    override fun getItemCount(): Int {
        return mData.size
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
}
