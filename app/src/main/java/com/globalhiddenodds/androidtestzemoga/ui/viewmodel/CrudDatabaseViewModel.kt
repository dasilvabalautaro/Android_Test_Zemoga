package com.globalhiddenodds.androidtestzemoga.ui.viewmodel

import androidx.lifecycle.*
import com.globalhiddenodds.androidtestzemoga.datasource.database.User
import com.globalhiddenodds.androidtestzemoga.datasource.database.toUserView
import com.globalhiddenodds.androidtestzemoga.domain.CrudDatabaseUseCase
import com.globalhiddenodds.androidtestzemoga.ui.data.CompanyView
import com.globalhiddenodds.androidtestzemoga.ui.data.UserCurrentView
import com.globalhiddenodds.androidtestzemoga.ui.data.UserView
import com.globalhiddenodds.androidtestzemoga.workers.PostWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Pattern Observer
// Pattern Adapter

@HiltViewModel
class CrudDatabaseViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val crudDatabaseUseCase: CrudDatabaseUseCase
): ViewModel() {
    private val taskResultMutableLive = MutableLiveData<String>()
    private val mutableUser = MutableLiveData<UserCurrentView>()
    private val mutableCompany = MutableLiveData<CompanyView>()
    val company: LiveData<CompanyView> = mutableCompany
    val user: LiveData<UserCurrentView> = mutableUser
    val taskResult: LiveData<String> = taskResultMutableLive
    val listPosts: LiveData<List<UserView>> by lazy {
        Transformations.map(
            crudDatabaseUseCase.listPosts.distinctUntilChanged()) {
                //it.map { it.toUserView() }
                transformUser(it)
            }
        }
    val listPostLike: LiveData<List<UserView>> by lazy {
        Transformations.map(
            crudDatabaseUseCase.listPostLike.distinctUntilChanged()) {
            it.map { it.toUserView() }

        }
    }

    private val viewStatus = "VIEW_STATUS_DOWN"

    init {
        handle[viewStatus] = false
    }

    private fun transformUser(list: List<User>): List<UserView>{
        val listUser = mutableListOf<UserView>()
        viewModelScope.launch(Dispatchers.IO){
            list.map { listUser.add(it.toUserView()) }
        }
        return listUser
    }
    fun updateLike(userId: Int, like: Boolean){
        viewModelScope.launch {
            val result = crudDatabaseUseCase.updateLike(userId, like)
            taskResultMutableLive.value = "Updated database. $result"
        }
    }

    fun getUser(id: Int){
        viewModelScope.launch{
            val result = crudDatabaseUseCase.getCurrentUser(id)
            if (result.isSuccess){
                mutableUser.value = result.getOrNull()
            }
            else{
                taskResultMutableLive.value = "User not exist. ${result.isFailure}"
            }
        }
    }

    fun getCompany(id: Int){
        viewModelScope.launch{
            val result = crudDatabaseUseCase.getCompany(id)
            if (result.isSuccess){
                mutableCompany.value = result.getOrNull()
            }
            else{
                taskResultMutableLive.value = "Company not exist. ${result.isFailure}"
            }
        }
    }

    fun insertToDatabase(){
        viewModelScope.launch {
            if(PostWorker.listPosts.isNotEmpty() && !handle.getLiveData<Boolean>(viewStatus).value!!){
                handle[viewStatus] = true
                var result = crudDatabaseUseCase.insertPosts()
                taskResultMutableLive.value = "Insert Posts. $result"
                result = crudDatabaseUseCase.insertUsers()
                taskResultMutableLive.value = "Insert Users. $result"

            }
        }
    }
}

