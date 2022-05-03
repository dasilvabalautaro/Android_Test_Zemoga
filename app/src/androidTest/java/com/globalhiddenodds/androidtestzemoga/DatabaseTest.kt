package com.globalhiddenodds.androidtestzemoga

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.globalhiddenodds.androidtestzemoga.datasource.database.AppRoomDatabase
import com.globalhiddenodds.androidtestzemoga.datasource.database.User
import com.globalhiddenodds.androidtestzemoga.repository.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDao
    private lateinit var db: AppRoomDatabase

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = db.userDao()
    }

    @After
    fun teardown(){
        db.close()
    }

    @Test
    fun insertUserPost() = runTest {
        val user = User(1, 2, 1, "Pico Palotes",
            "sed rtgb yhum juio", false)
        userDao.insert(user)
        val userInserted = userDao.getList().asLiveData().getOrAwaitValue()

        assertThat(userInserted.size).isEqualTo(1)

    }
}