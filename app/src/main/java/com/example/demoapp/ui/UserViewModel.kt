package com.example.demoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import com.example.demoapp.data.remote.ReqresApiClient
import com.example.demoapp.repositories.DefaultUserRepository
import com.example.demoapp.repositories.UserDataStoreRepository
import kotlinx.coroutines.launch

class UserViewModel(userDataStoreRepository: UserDataStoreRepository): ViewModel() {
    private val api = ReqresApiClient
    private val userRepository = DefaultUserRepository(userDataStoreRepository,api.reqresApiService)

    val userList : LiveData<UserList> = userRepository.observeAllUsers().asLiveData()
    fun deleteUser(user: User) = viewModelScope.launch{
        userRepository.deleteUser(user)
    }

    fun deleteAllUsers()= viewModelScope.launch{
        userRepository.deleteAllUsers()
    }

    fun fetchAllUsers()= viewModelScope.launch {
        userRepository.fetchAllUsers()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return UserViewModel(UserDataStoreRepository(application)) as T
            }
        }
    }

}