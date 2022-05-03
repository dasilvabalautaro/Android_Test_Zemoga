package com.globalhiddenodds.androidtestzemoga.ui.viewmodel

import androidx.lifecycle.*
import androidx.work.WorkInfo
import com.globalhiddenodds.androidtestzemoga.domain.DownPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownPostsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val downPostsUseCase: DownPostsUseCase
) : ViewModel() {
    val outputWorkInfo: LiveData<List<WorkInfo>> = downPostsUseCase.workInfo

    fun downPosts() {
        viewModelScope.launch {
            downPostsUseCase.downPosts()
        }
    }

    fun cancelWork() {
        viewModelScope.launch {
            downPostsUseCase.cancelWork()
        }
    }

}