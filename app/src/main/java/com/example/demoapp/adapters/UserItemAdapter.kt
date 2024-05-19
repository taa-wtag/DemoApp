package com.example.demoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.UserList.User
import com.example.demoapp.databinding.UserListItemBinding

class UserItemAdapter: RecyclerView.Adapter<UserItemViewHolder>() {


    private val differ = AsyncListDiffer(this, UserItemAdapterDiffCallback())

    var userItems: List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserItemViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(userItems[position])
    }

    override fun getItemCount(): Int {
        return userItems.size
    }

}