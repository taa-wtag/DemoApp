package com.example.demoapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.demoapp.UserList
import com.example.demoapp.other.Constants

object DataStoreSingleton {
    val Context.dataStore: DataStore<UserList> by dataStore(
        fileName = Constants.DATASTORE_FILE_NAME,
        serializer = UserListSerializer
    )
}