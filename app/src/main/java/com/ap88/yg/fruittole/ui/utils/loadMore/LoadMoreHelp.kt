package com.ap88.yg.fruittole.ui.utils.loadMore

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

/**
 * Created by duanlei on 2018/2/27.
 */
class LoadMoreHelp(mILoadMore: ILoadMore) {

    companion object {
        const val PAGE_SIZE = 15
    }

    private var isLoading: Boolean = false //是否正在加载
    private var isEnd: Boolean = false //是否加载完所有列表数据

    public val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
            val visibleItemsCount = layoutManager.childCount
            val totalItemsCount = layoutManager.itemCount
            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading && !isEnd &&
                    visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                //加载更多
                mILoadMore.loadMore()
                isLoading = true
            }
        }
    }

    public fun refresh() {
        isEnd = false
    }
    public fun complete(listSize: Int, context: Context?) {
        isLoading = false
        if (listSize < PAGE_SIZE) {
            isEnd = true
            if (context != null) {
                Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show()
            }
        }
    }
    public fun loadError() {
        isLoading = false
    }

}