package com.globalhiddenodds.androidtestzemoga.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globalhiddenodds.androidtestzemoga.datasource.database.UserCurrent

// Pattern Observer
@Dao
interface UserCurrentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserCurrent)

    @Query("SELECT * FROM userCurrent WHERE id = :id")
    fun getUser(id: Int): UserCurrent

    @Query("DELETE FROM userCurrent")
    suspend fun deleteTable()
}