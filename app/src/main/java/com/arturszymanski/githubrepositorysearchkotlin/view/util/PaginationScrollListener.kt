package com.arturszymanski.githubrepositorysearchkotlin.view.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

const val VISIBLE_THRESHOLD = 5

class PaginationScrollListener @Inject constructor() : RecyclerView.OnScrollListener() {

    var action: PaginationActionsListener? = null

    var isDataLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy < 0 || isDataLoading) {
            return
        }

        if(recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()
            val itemCount = recyclerView.adapter?.itemCount ?: 0

            if(lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD) {
                action?.loadMore()
            }
        }
    }
}

interface PaginationActionsListener {
    fun loadMore()
}