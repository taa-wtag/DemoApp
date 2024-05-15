package com.example.demoapp.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.core.IOException
import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import com.example.demoapp.data.remote.response.UserResponse
import com.example.demoapp.repositories.DataStoreSingleton.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull


class UserDataStoreRepository(private val context: Context): DataStoreRepository {
    val dataStore = DataStoreSingleton
    private val userListFlow: Flow<UserList> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("Error", exception.toString())
                emit(UserList.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun addData(t: Any) {
        when (t) {
            is User -> {
                    if(!checkIfUserExists(t.id)) {
                    context.dataStore.updateData { preferences ->
                        preferences.toBuilder().addUsers(t).build()
                    }
                }
                else throw Exception("User exists!")
            }
            is UserList -> {
                context.dataStore.updateData { preferences ->
                    preferences.toBuilder().addAllUsers(t.usersList.filter { !checkIfUserExists(it.id) }).build()
                }
            }

            is UserResponse -> {
                if(!checkIfUserExists(t.id)) {
                    context.dataStore.updateData { preferences ->
                        val newUser = User.newBuilder()
                        newUser.setAvatar(t.avatar)
                        newUser.setEmail(t.email)
                        newUser.setId(t.id)
                        newUser.setFirstName(t.firstName)
                        newUser.setLastName(t.lastName)
                        preferences.toBuilder().addUsers(newUser).build()
                    }
                }
            }

            else -> throw Exception("Invalid type argument!")
        }
    }

    override suspend fun deleteData(t: Any) {
        if(t is User) {
            val userIndex = userListFlow.first().usersList.indexOf(t)
            if(userIndex!=-1) {
                context.dataStore.updateData { preferences ->
                    preferences.toBuilder().removeUsers(userIndex).build()
                }
            }
            else throw Exception("No such User exists!")
        }
        else throw Exception("Invalid type argument!")
    }

    override suspend fun deleteAllData() {
        context.dataStore.updateData { preferences ->
            preferences.toBuilder().clearUsers().build()
        }
    }

    override fun getData(): Flow<Any> {
        return userListFlow
    }

    private suspend fun checkIfUserExists(id: Int): Boolean{
        val user = userListFlow.firstOrNull()?.usersList?.find {
            it.id == id
        }
        return user!=null
    }
}