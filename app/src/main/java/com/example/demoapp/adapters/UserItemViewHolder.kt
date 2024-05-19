package com.example.demoapp.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.UserList
import com.example.demoapp.databinding.UserListItemBinding

class UserItemViewHolder(private val binding: UserListItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(userItem: UserList.User){
        val name = (userItem.firstName?:"") + (userItem.lastName?:"")
        binding.textViewName.text = name
        binding.textViewEmail.text = userItem.email?:""
    }
}