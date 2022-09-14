package com.boys.assets.accenture.di.network

import com.boys.assets.accenture.activity.fragment.popular.model.PopularModel
import com.boys.assets.accenture.activity.fragment.popular.model.PopularReqModel
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.users.model.ReposModel
import com.boys.assets.accenture.activity.users.model.UsersModel

interface Repository {

    suspend fun getAllUsers(): List<Users>

    suspend fun getUsers(token: String, users: String): UsersModel

    suspend fun getRepos(token: String, users: String): ArrayList<ReposModel>

    // for new structures

    suspend fun getPopular(token: String, model: PopularReqModel): PopularModel

    // from local
    suspend fun getAll() : List<Users>

}