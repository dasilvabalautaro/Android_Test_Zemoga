package com.globalhiddenodds.androidtestzemoga.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.globalhiddenodds.androidtestzemoga.workers.DOWNLOAD_POSTS_WORK_NAME
import com.globalhiddenodds.androidtestzemoga.workers.PostWorker
import com.globalhiddenodds.androidtestzemoga.workers.TAG_OUTPUT
import com.globalhiddenodds.androidtestzemoga.workers.UserWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownPostsUseCase @Inject constructor(
    @ApplicationContext val context: Context) {
    private val workManager = WorkManager.getInstance(context)
    val workInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)

    fun downPosts() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val downUsersRequest = OneTimeWorkRequest.Builder(UserWorker::class.java)
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()

        workManager.beginUniqueWork(
            DOWNLOAD_POSTS_WORK_NAME, ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.Builder(PostWorker::class.java)
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)
                .build()).then(downUsersRequest).enqueue()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(DOWNLOAD_POSTS_WORK_NAME)
    }

}
