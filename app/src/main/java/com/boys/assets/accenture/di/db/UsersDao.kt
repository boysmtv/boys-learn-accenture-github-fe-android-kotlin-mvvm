package com.boys.assets.accenture.di.db

import androidx.room.*
import com.boys.assets.accenture.activity.fragment.popular.model.Users

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Users)

    @Delete
    fun delete(users: Users)

    @Query("select count(*) from users where id = :id")
    fun getByID(id: Int): Int

    @Query("select * from users order by id asc")
    fun getAll(): List<Users>

}