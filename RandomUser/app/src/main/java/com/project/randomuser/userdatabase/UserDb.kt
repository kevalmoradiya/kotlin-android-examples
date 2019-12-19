package com.project.randomuser.userdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.randomuser.networking.UserDetail


@Database(
    entities = [UserDetail::class],
    version = 1,
    exportSchema = false
)
abstract class UserDb : RoomDatabase() {

    companion object {
        fun create(context: Context): UserDb {
            val databaseBuilder = Room.databaseBuilder(context, UserDb::class.java, "randomuser.db")
            return databaseBuilder.build()
        }
    }

    abstract fun userdetailDao(): UserDao
}