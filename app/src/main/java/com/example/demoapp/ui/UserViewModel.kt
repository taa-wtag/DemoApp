package com.example.demoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import com.example.demoapp.data.remote.ReqresApiClient
import com.example.demoapp.repositories.DataStoreRepository
import com.example.demoapp.repositories.DefaultUserRepository
import kotlinx.coroutines.launch

class UserViewModel(userDataStoreRepository: DataStoreRepository): ViewModel() {
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
        userRepository.fetchAllUsersFromRemote()
    }

    class UserViewModelFactory(private val userDataStoreRepository: DataStoreRepository) :
        ViewModelProvider.Factory{
            override fun <T:ViewModel> create (modelClass: Class<T>):T{
                if(modelClass.isAssignableFrom((UserViewModel::class.java))){
                    @Suppress("UNCHECKED_CAST")
                    return UserViewModel(userDataStoreRepository) as T
                }
                throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
            }
        }


}