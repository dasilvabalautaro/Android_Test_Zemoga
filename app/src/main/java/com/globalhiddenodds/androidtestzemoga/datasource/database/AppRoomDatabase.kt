package com.globalhiddenodds.androidtestzemoga.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.globalhiddenodds.androidtestzemoga.repository.CommentDao
import com.globalhiddenodds.androidtestzemoga.repository.CompanyDao
import com.globalhiddenodds.androidtestzemoga.repository.UserCurrentDao
import com.globalhiddenodds.androidtestzemoga.repository.UserDao

@Database(entities = [User::class, Comment::class, UserCurrent::class, Company::class], version = 5, exportSchema = false)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun commentDao(): CommentDao
    abstract fun userDao(): UserDao
    abstract fun userCurrentDao(): UserCurrentDao
    abstract fun companyDao(): CompanyDao
}