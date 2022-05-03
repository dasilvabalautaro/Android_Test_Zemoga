package com.globalhiddenodds.androidtestzemoga.datasource.network.data

import com.globalhiddenodds.androidtestzemoga.datasource.database.User
import com.squareup.moshi.Json

data class User(@field:Json(name = "userId") val userId: Int,
                @field:Json(name = "id") val id: Int,
                @field:Json(name = "title") val title: String,
                @field:Json(name = "body") val body: String){
    fun toUserDatabase(): User{
        return User(0, userId, id, title, body, false)
    }

}
