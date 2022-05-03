package com.globalhiddenodds.androidtestzemoga.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import com.globalhiddenodds.androidtestzemoga.datasource.database.Company
import com.globalhiddenodds.androidtestzemoga.datasource.database.User
import com.globalhiddenodds.androidtestzemoga.datasource.database.toCompanyView
import com.globalhiddenodds.androidtestzemoga.datasource.database.toUserCurrentView
import com.globalhiddenodds.androidtestzemoga.repository.CommentDao
import com.globalhiddenodds.androidtestzemoga.repository.CompanyDao
import com.globalhiddenodds.androidtestzemoga.repository.UserCurrentDao
import com.globalhiddenodds.androidtestzemoga.repository.UserDao
import com.globalhiddenodds.androidtestzemoga.ui.data.CompanyView
import com.globalhiddenodds.androidtestzemoga.ui.data.UserCurrentView
import com.globalhiddenodds.androidtestzemoga.workers.PostWorker
import com.globalhiddenodds.androidtestzemoga.workers.UserWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Pattern adapter
// Pattern Observer

class CrudDatabaseUseCase @Inject constructor(
    private val userDao: UserDao,
    private val commentDao: CommentDao,
    private val userCurrentDao: UserCurrentDao,
    private val companyDao: CompanyDao
) {
    val listPosts: LiveData<List<User>> = userDao.getList().asLiveData().distinctUntilChanged()
    val listPostLike: LiveData<List<User>> =
        userDao.getListLike(true).asLiveData().distinctUntilChanged()

    suspend fun cleanDatabase(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            userDao.deleteTable()
            commentDao.deleteComments()
            userCurrentDao.deleteTable()
            companyDao.deleteTable()
            return@withContext Result.success(true)
        }
    }

    suspend fun updateLike(userId: Int, like: Boolean): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            userDao.updateLike(userId, like)
            return@withContext Result.success(true)
        }
    }

    suspend fun deleteUser(userId: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            userDao.deleteUser(userId)
            return@withContext Result.success(true)
        }
    }

    suspend fun deleteCommentUser(id: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            userDao.deleteComment(id)
            return@withContext Result.success(true)
        }
    }

    suspend fun deleteComment(id: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            commentDao.deleteCommentOfPerson(id)
            return@withContext Result.success(true)
        }
    }

    suspend fun getCurrentUser(id: Int): Result<UserCurrentView> {
        return withContext(Dispatchers.IO) {
            val user = userCurrentDao.getUser(id)
            if (user.id == id) {
                val userView = user.toUserCurrentView()
                return@withContext Result.success(userView)
            } else {
                return@withContext Result.failure(Exception("User not exist"))
            }
        }
    }

    suspend fun getCompany(id: Int): Result<CompanyView> {
        return withContext(Dispatchers.IO) {
            val company = companyDao.getCompany(id)
            if (company.id == id) {
                val companyView = company.toCompanyView()
                return@withContext Result.success(companyView)
            } else {
                return@withContext Result.failure(Exception("Company not exist"))
            }
        }
    }

    suspend fun insertPosts(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            val list = PostWorker.listPosts
            when {
                !list.isNullOrEmpty() -> {
                    list.forEach { userDao.insert(it.toUserDatabase()) }
                    PostWorker.listPosts = listOf()
                    return@withContext Result.success(true)
                }
                else -> {
                    return@withContext Result.success(false)
                }
            }

        }
    }

    suspend fun insertUsers(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            val list = UserWorker.listUsers
            when {
                !list.isNullOrEmpty() -> {
                    list.forEach {
                        userCurrentDao.insert(it.toUserCurrentDatabase())
                        companyDao.insert(it.toCompanyDatabase())
                    }
                    UserWorker.listUsers = listOf()
                    return@withContext Result.success(true)
                }
                else -> {
                    return@withContext Result.success(false)
                }
            }

        }
    }

}