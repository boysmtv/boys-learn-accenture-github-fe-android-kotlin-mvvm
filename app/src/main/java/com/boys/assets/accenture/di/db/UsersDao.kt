package com.boys.assets.accenture.di.db

import androidx.room.*
import com.boys.assets.accenture.activity.fragment.popular.model.Users

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Users)

    @Delete
    fun delete(users: Users)

    @Query("select count(*) from users where login = :login")
    fun getByID(login: String): Int

    @Query("select * from users order by id asc")
    fun getAll(): List<Users>

}