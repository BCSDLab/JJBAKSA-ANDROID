package com.jjbaksa.jjbaksa.util

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ItemMarginDecoration(private val orientation: Int, private val margin: Int) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (orientation) {
            HORIZONTAL -> {
                val itemCount = state.itemCount
                val currentPosition = parent.getChildAdapterPosition(view)
                when {
                    currentPosition == 0 -> {
                        // First item
                        outRect.right = margin
                    }
                    currentPosition > 0 && currentPosition == itemCount - 1 -> {
                        // Last item
                        outRect.left = margin
                    }
                    else -> {
                        // Other items
                        outRect.left = margin
                        outRect.right = margin
                    }
                }
            }
            VERTICAL -> {
                val itemCount = state.itemCount
                val currentPosition = parent.getChildAdapterPosition(view)
                when {
                    currentPosition == 0 -> {
                        // First item
                        outRect.bottom = margin
                    }
                    currentPosition > 0 && currentPosition == itemCount - 1 -> {
                        // Last item
                        outRect.top = margin
                    }
                    else -> {
                        // Other items
                        outRect.top = margin
                        outRect.bottom = margin
                    }
                }
            }
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }
}
