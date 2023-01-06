package com.jjbaksa.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jjbaksa.data.SEARCH_HISTORY_TABLE
import com.jjbaksa.data.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM $SEARCH_HISTORY_TABLE")
    fun getAll(): List<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchHistoryEntity: SearchHistoryEntity)
}
