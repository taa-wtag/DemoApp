package com.example.demoapp.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.demoapp.UserList
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserListSerializer: Serializer<UserList> {
    override val defaultValue: UserList = UserList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserList {
        return try {
            UserList.parseFrom(input)

        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UserList, output: OutputStream) {
        t.writeTo(output)
    }
}