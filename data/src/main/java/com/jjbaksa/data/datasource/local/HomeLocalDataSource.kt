package com.jjbaksa.data.datasource.local

import android.content.Context
import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.datasource.HomeDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserDao
) : HomeDataSource
