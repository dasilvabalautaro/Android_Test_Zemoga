package com.globalhiddenodds.androidtestzemoga.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globalhiddenodds.androidtestzemoga.datasource.database.User
import kotlinx.coroutines.flow.Flow

// Pattern Observer
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: User)

    @Query("SELECT * FROM user ORDER BY isLike DESC")
    fun getList(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE isLike = :like ORDER BY userId")
    fun getListLike(like: Boolean): Flow<List<User>>

    @Query("UPDATE user SET isLike = :like WHERE userId = :userId")
    suspend fun updateLike(userId: Int, like: Boolean)

    @Query("DELETE FROM user WHERE userId = :userId")
    suspend fun deleteUser(userId: Int)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteComment(id: Int)

    @Query("DELETE FROM user")
    suspend fun deleteTable()

}