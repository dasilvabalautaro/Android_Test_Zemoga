package com.globalhiddenodds.androidtestzemoga.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globalhiddenodds.androidtestzemoga.ui.data.CommentView

@Entity(tableName = "comment")
data class Comment(
    @PrimaryKey(autoGenerate = true) val commId: Int = 0,
    @ColumnInfo(name = "postId")
    val postId: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "body")
    var body: String
)

fun Comment.toCommentView():
        CommentView = CommentView(
    commId, postId, name, email, body)