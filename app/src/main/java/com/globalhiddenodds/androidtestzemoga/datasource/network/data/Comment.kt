package com.globalhiddenodds.androidtestzemoga.datasource.network.data

import com.globalhiddenodds.androidtestzemoga.datasource.database.Comment
import com.squareup.moshi.Json

data class Comment(@field:Json(name = "postId") val postId: Int,
                   @field:Json(name = "id") val id: Int,
                   @field:Json(name = "name") val name: String,
                   @field:Json(name = "email") val email: String,
                   @field:Json(name = "body") val body: String) {
    fun toCommentDatabase(): Comment{
        return Comment(0, postId, id, name, email, body)
    }
}