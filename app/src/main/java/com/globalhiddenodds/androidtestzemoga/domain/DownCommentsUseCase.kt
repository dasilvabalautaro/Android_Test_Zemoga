package com.globalhiddenodds.androidtestzemoga.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import com.globalhiddenodds.androidtestzemoga.datasource.database.Comment
import com.globalhiddenodds.androidtestzemoga.datasource.network.CommentsApi
import com.globalhiddenodds.androidtestzemoga.repository.CommentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class CommentsApiStatus { LOADING, ERROR, DONE, EMPTY }

// Pattern adapter
// Pattern Observer

class DownCommentsUseCase @Inject constructor(
    private val commentDao: CommentDao)
{
    private val refreshIntervalMilliseconds: Long = 50
    private val commentStatus = MutableLiveData<CommentsApiStatus>()
    val status: LiveData<CommentsApiStatus> by lazy { commentStatus.distinctUntilChanged() }
    private val commentsMutable = MutableLiveData<List<Comment>>()
    val listComment: LiveData<List<Comment>> by lazy { commentsMutable.distinctUntilChanged() }

    init {
        commentStatus.value = CommentsApiStatus.EMPTY
    }

    private fun setStatus(downStatus: CommentsApiStatus){
        commentStatus.postValue(downStatus)
    }

    private suspend fun downCommentsById(userId: Int){
        withContext(Dispatchers.IO){
            try {
                setStatus(CommentsApiStatus.LOADING)
                val list = CommentsApi.retrofitService.getComments(userId)
                when {
                    !list.isNullOrEmpty() -> {
                        list.forEach { commentDao.insert(it.toCommentDatabase()) }
                        setStatus(CommentsApiStatus.DONE)
                    }
                    else -> {
                        setStatus(CommentsApiStatus.EMPTY)
                    }
                }

            }catch(e: Exception){
                setStatus(CommentsApiStatus.ERROR)
            }
        }
    }

    suspend fun getCommentsById(userId: Int){
        withContext(Dispatchers.IO){
            val size = commentDao.getCommentsSingle(userId).size
            if(size == 0){
                downCommentsById(userId)
                Log.i("DOWN COMMENT", userId.toString())
            }

        }
        commentsMutable.value = listOf()
        commentDao.getComments(userId).collect { value ->
//            commentsMutable.value = value
//            delay(refreshIntervalMilliseconds)
//            commentsMutable.value = value
            delay(refreshIntervalMilliseconds)
            commentsMutable.value = value

        }

    }
}