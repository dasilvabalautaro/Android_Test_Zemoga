package com.globalhiddenodds.androidtestzemoga.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globalhiddenodds.androidtestzemoga.ui.data.UserCurrentView

@Entity(tableName = "userCurrent")
data class UserCurrent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "website")
    var website: String,
    @ColumnInfo(name = "phone")
    var phone: String
)

fun UserCurrent.toUserCurrentView():
        UserCurrentView = UserCurrentView(
    id, name, email, website, phone)