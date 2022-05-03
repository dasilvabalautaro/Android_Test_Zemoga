package com.globalhiddenodds.androidtestzemoga.ui.viewmodel

import androidx.lifecycle.*
import com.globalhiddenodds.androidtestzemoga.domain.CrudDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteDatabaseViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val crudDatabaseUseCase: CrudDatabaseUseCase
): ViewModel()  {
    private val taskResultMutableLive = MutableLiveData<String>()
    val taskResult: LiveData<String> = taskResultMutableLive

    fun deleteUser(userId: Int){
        viewModelScope.launch {
            val result = crudDatabaseUseCase.deleteUser(userId)
            taskResultMutableLive.value = "Deleted user. $result"
        }
    }

    fun cleanDatabase(){
        viewModelScope.launch{
            val result = crudDatabaseUseCase.cleanDatabase()
            taskResultMutableLive.value = "Database clear. $result"
        }
    }

    fun deleteComment(id: Int){
        viewModelScope.launch {
            var result = crudDatabaseUseCase.deleteCommentUser(id)
            taskResultMutableLive.value = "Deleted comment user. $result"
            result = crudDatabaseUseCase.deleteComment(id)
            taskResultMutableLive.value = "Deleted comment. $result"
        }
    }

}