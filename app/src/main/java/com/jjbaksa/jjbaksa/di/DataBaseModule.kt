package com.jjbaksa.jjbaksa.di

import android.content.Context
import androidx.room.Room
import com.jjbaksa.data.database.AppDatabase
import com.jjbaksa.data.database.SearchHistoryDao
import com.jjbaksa.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "MyExoPlayer.db")
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    @Singleton
    fun provideVideoDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDao(appDatabase: AppDatabase): SearchHistoryDao {
        return appDatabase.searchHistoryDao()
    }
}
