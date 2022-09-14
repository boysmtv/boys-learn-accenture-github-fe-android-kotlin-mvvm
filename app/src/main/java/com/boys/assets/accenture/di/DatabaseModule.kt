package com.boys.assets.accenture.di

import android.app.Application
import androidx.room.Room
import com.boys.assets.accenture.di.db.UsersDao
import com.boys.assets.accenture.di.db.UsersDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): UsersDatabase {
        return Room.databaseBuilder(application, UsersDatabase::class.java, UsersDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: UsersDatabase): UsersDao {
        return database.usersDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}