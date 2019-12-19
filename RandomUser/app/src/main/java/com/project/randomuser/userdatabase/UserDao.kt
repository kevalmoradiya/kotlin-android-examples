package com.project.randomuser.userdatabase


import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.randomuser.networking.UserDetail


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<UserDetail>)


    //all user
    @Query("SELECT * FROM UserDetail")
    fun users(): DataSource.Factory<Int, UserDetail>
    //user by name
    @Query("SELECT * FROM UserDetail where first LIKE  :name")
    fun userByName(name: String?): DataSource.Factory<Int, UserDetail>
}
