package com.jjbaksa.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jjbaksa.data.SEARCH_HISTORY_TABLE

@Entity(tableName = SEARCH_HISTORY_TABLE)
data class SearchHistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "search_keyword")
    val searchKeyword: String
)
