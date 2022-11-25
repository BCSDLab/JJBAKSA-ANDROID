package com.jjbaksa.data.mapper

import com.jjbaksa.data.entity.SearchHistoryEntity

object SearchHistoryMapper {
    fun List<SearchHistoryEntity>.mapToSearchHistoryResult(): List<String> {
        val list = mutableListOf<String>()
        for (i in this) {
            list.add(i.searchKeyword)
        }

        return list
    }
}
