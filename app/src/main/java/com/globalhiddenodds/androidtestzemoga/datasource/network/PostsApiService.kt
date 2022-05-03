package com.globalhiddenodds.androidtestzemoga.datasource.network

import com.globalhiddenodds.androidtestzemoga.datasource.network.data.Comment
import com.globalhiddenodds.androidtestzemoga.datasource.network.data.User
import com.globalhiddenodds.androidtestzemoga.datasource.network.data.UserCurrent
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val baseUrl = "https://jsonplaceholder.typicode.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

// Pattern abstract factory

interface PostsApiService {
    @GET("posts")
    suspend fun getPosts(): List<User>
}

interface CommentsApiService{
    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): List<Comment>
}

interface UserApiService{
    @GET("users")
    suspend fun getUsers(): List<UserCurrent>
}

// Pattern singleton

object PostApi{
    val retrofitService: PostsApiService by lazy {
        retrofit.create(PostsApiService::class.java)
    }
}

object CommentsApi{
    val retrofitService: CommentsApiService by lazy {
        retrofit.create(CommentsApiService::class.java)
    }
}

object UserApi{
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}
