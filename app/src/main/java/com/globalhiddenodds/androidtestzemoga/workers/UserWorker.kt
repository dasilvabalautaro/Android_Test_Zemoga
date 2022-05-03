package com.globalhiddenodds.androidtestzemoga.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.datasource.network.UserApi
import com.globalhiddenodds.androidtestzemoga.datasource.network.data.UserCurrent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Pattern adapter
class UserWorker @Inject constructor(
    @ApplicationContext val context: Context,
    workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification(context.getString(R.string.lbl_download_users),
            applicationContext)
        return withContext(Dispatchers.IO) {
            return@withContext try {
                listUsers = UserApi.retrofitService.getUsers()
                var isSuccess = true
                if (listUsers.isEmpty()) isSuccess = false
                val outputData: Data = workDataOf(KEY_IS_SUCCESS to isSuccess)
                Result.success(outputData)
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }

    companion object{
        var listUsers: List<UserCurrent> = listOf()
    }
}