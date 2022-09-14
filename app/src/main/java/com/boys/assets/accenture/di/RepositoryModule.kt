package com.boys.assets.accenture.di

import com.boys.assets.accenture.di.db.UsersDao
import com.boys.assets.accenture.remote.ApiService
import com.boys.assets.accenture.di.network.Repository
import com.boys.assets.accenture.di.network.RepositoryImpl

fun createRepository(apiService: ApiService, usersDao: UsersDao): Repository {
    return RepositoryImpl(apiService, usersDao)
}