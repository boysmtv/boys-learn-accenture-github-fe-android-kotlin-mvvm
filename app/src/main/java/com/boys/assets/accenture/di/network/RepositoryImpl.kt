package com.boys.assets.accenture.di.network

import com.boys.assets.accenture.activity.fragment.popular.model.PopularModel
import com.boys.assets.accenture.activity.fragment.popular.model.PopularReqModel
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.users.model.ReposModel
import com.boys.assets.accenture.activity.users.model.UsersModel
import com.boys.assets.accenture.di.db.UsersDao
import com.boys.assets.accenture.remote.ApiService

class RepositoryImpl (private val apiService: ApiService, private val usersDao: UsersDao) : Repository {

    override suspend fun getAllUsers(): List<Users> {
        return apiService.getAllUsers()
    }

    override suspend fun getUsers(token: String, users: String): UsersModel {
        return apiService.getUsers(token, users)
    }

    override suspend fun getRepos(token: String, users: String): ArrayList<ReposModel> {
        return apiService.getRepos(token, users)
    }

    override suspend fun getPopular(token: String, model: PopularReqModel): PopularModel {
        return apiService.getPopular(token, model.q, model.sort, model.per_page, model.page)
    }

    override suspend fun getAll(): List<Users> {
        return usersDao.getAll()
    }

}