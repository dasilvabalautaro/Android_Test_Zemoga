package com.globalhiddenodds.androidtestzemoga

import com.globalhiddenodds.androidtestzemoga.datasource.network.PostsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

class PostApiTest {

    @get:Rule
    var rule = OkHttpIdlingResourceRule()

    private val mockWebServer = MockWebServer()
    private lateinit var apiService: PostsApiService
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Before
    fun setup() {
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(PostsApiService::class.java)
    }

    @After
    fun teardown(){
        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowPersonCorrectly() = runBlocking {
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                FileReader
                .readStringFromFile(
                    "success_response.json")))
        val response = apiService.getPosts()

        assert(2 == response.size)
        assert(1 == response[0].id)
        assert(2 == response[1].id)
    }

    @Test
    fun shouldShowError(){
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(response)

        assert(!response.status.contains("200"))
    }

}