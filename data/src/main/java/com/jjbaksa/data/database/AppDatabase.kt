package com.jjbaksa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jjbaksa.data.entity.UserEntity

@Database(
    entities = [UserEntity::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
