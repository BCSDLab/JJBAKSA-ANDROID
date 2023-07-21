package com.jjbaksa.jjbaksa.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val pageSize: Int
) : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var totalItemCount = layoutManager.itemCount.minus(1)
        var lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        if (totalItemCount == lastPosition) {
            loading()
            loadMoreItems()
        }
    }

    abstract fun loading()
    abstract fun loadMoreItems()
}
