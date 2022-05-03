package com.globalhiddenodds.androidtestzemoga.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globalhiddenodds.androidtestzemoga.datasource.database.Comment
import kotlinx.coroutines.flow.Flow

// Pattern Observer
@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comment)

    @Query("SELECT * FROM comment WHERE postId = :postId ORDER BY id ASC")
    fun getComments(postId: Int): Flow<List<Comment>>

    @Query("SELECT * FROM comment WHERE postId = :postId ORDER BY id ASC")
    suspend fun getCommentsSingle(postId: Int): List<Comment>

    @Query("DELETE FROM comment WHERE id = :id")
    suspend fun deleteCommentOfPerson(id: Int)

    @Query("DELETE FROM comment")
    suspend fun deleteComments()

}