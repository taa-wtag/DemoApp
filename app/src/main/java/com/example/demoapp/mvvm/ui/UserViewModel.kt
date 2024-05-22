package com.example.demoapp.mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import com.example.demoapp.data.remote.UserApiClient
import com.example.demoapp.mvvm.repositories.DefaultUserRepository
import com.example.demoapp.mvvm.repositories.IDataStoreRepository
import kotlinx.coroutines.launch

class UserViewModel(userDataStoreRepository: IDataStoreRepository): ViewModel() {
    private val api = UserApiClient
    private val userRepository = DefaultUserRepository(userDataStoreRepository,api.userApiService)

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

    class UserViewModelFactory(private val userDataStoreRepository: IDataStoreRepository) :
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