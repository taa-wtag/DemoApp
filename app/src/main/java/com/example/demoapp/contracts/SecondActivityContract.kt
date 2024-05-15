package com.example.demoapp.contracts

import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import kotlinx.coroutines.flow.Flow

interface SecondActivityContract {
    interface View{
    }

    interface Presenter{
        fun getAllUsers()
        fun deleteUser(user: User)
        fun deleteAllUsers()
        fun onDestroy()
    }

    interface Model{
        interface OnFinishListener{
            fun onLoading()
            fun onError (message: String)
            fun onSuccess(list: UserList)
        }

        suspend fun deleteUser(user: User)

        suspend fun deleteAllUsers()

        fun observeAllUsers(): Flow<UserList>

        suspend fun fetchAllUsersFromRemote()
    }
}