package com.example.demoapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.demoapp.UserList.User

class UserItemAdapterDiffCallback: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}