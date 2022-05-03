package com.globalhiddenodds.androidtestzemoga.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.datasource.network.PostApi
import com.globalhiddenodds.androidtestzemoga.datasource.network.data.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Pattern adapter
class PostWorker @Inject constructor(
    @ApplicationContext val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification(
            context.getString(R.string.lbl_download_posts),
            applicationContext
        )
        return withContext(Dispatchers.IO) {

            return@withContext try {
                listPosts = PostApi.retrofitService.getPosts()
                var isSuccess = true
                if (listPosts.isEmpty()) isSuccess = false
                val outputData: Data = workDataOf(KEY_IS_SUCCESS to isSuccess)
                Result.success(outputData)
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }

    companion object{
        var listPosts: List<User> = listOf()
    }
}