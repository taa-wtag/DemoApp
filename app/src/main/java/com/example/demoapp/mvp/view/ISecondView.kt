package com.example.demoapp.mvp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.UserList.User
import com.example.demoapp.databinding.ActivitySecondBinding

interface ISecondView {
    fun getContext(): Context
    fun getBinding(): ActivitySecondBinding
    fun getActivity(): AppCompatActivity
    fun getAllItems()
    fun deleteAllItems()
    fun deleteItem(user: User)
}