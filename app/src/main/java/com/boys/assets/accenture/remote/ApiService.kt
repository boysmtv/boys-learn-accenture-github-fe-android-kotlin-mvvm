package com.boys.assets.accenture.remote

import com.boys.assets.accenture.activity.fragment.popular.model.PopularModel
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.users.model.ReposModel
import com.boys.assets.accenture.activity.users.model.UsersModel
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getAllUsers() : List<Users>

    @Headers("Content-Type: application/json")
    @GET("users/{users}")
    suspend fun getUsers(
        @Header("Authorization") token: String?,
        @Path("users") users: String?
    ) : UsersModel

    @Headers("Content-Type: application/json")
    @GET("users/{users}/repos")
    suspend fun getRepos(
        @Header("Authorization") token: String?,
        @Path("users") users: String?
    ) : ArrayList<ReposModel>

    @Headers("Content-Type: application/json")
    @GET("search/users")
    suspend fun getPopular(
        @Header("Authorization") token: String?,
        @Query("q") query: String?,
        @Query("sort") sort: String?,
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
    ) : PopularModel

}