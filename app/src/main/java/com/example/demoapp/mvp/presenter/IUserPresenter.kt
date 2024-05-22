package com.example.demoapp.mvp.presenter

import com.example.demoapp.UserList

interface IUserPresenter {
    suspend fun getAllUsers()
    suspend fun deleteUser(user: UserList.User)
    suspend fun deleteAllUsers()
    fun openMainActivity()
    fun onDestroy()
}