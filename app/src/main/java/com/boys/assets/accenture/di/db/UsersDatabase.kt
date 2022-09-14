package com.boys.assets.accenture.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boys.assets.accenture.activity.fragment.popular.model.Users

@Database(entities = [Users::class], version = UsersDatabase.VERSION)
abstract class UsersDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "users.db"
        const val VERSION = 1
    }

    abstract val usersDao: UsersDao

}