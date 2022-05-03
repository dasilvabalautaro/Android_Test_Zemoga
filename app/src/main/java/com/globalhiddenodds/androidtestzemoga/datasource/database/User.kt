package com.globalhiddenodds.androidtestzemoga.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globalhiddenodds.androidtestzemoga.ui.data.UserView

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val postId: Int = 0,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "body")
    var body: String,
    @ColumnInfo(name = "isLike")
    var isLike: Boolean
)

fun User.toUserView(): UserView = UserView(userId, id, title, body, isLike)
