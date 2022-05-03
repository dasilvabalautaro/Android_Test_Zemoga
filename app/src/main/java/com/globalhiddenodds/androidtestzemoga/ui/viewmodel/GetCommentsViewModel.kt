package com.globalhiddenodds.androidtestzemoga.ui.viewmodel

import androidx.lifecycle.*
import com.globalhiddenodds.androidtestzemoga.datasource.database.toCommentView
import com.globalhiddenodds.androidtestzemoga.domain.CommentsApiStatus
import com.globalhiddenodds.androidtestzemoga.domain.DownCommentsUseCase
import com.globalhiddenodds.androidtestzemoga.ui.data.CommentView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Pattern observer

@HiltViewModel
class GetCommentsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val downCommentsUseCase: DownCommentsUseCase
): ViewModel() {
    val listComment: LiveData<List<CommentView>> by lazy {
        Transformations.map(
            downCommentsUseCase.listComment.distinctUntilChanged()) {
            it.map { it.toCommentView() }
        }
    }
    val status: LiveData<CommentsApiStatus> by lazy { downCommentsUseCase.status.distinctUntilChanged() }

    fun getCommentsById(userId: Int){
        viewModelScope.launch{
            downCommentsUseCase.getCommentsById(userId)
        }
    }
}