package com.example.demoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.UserList.User
import com.example.demoapp.databinding.UserListItemBinding

class UserItemAdapter: RecyclerView.Adapter<UserItemAdapter.UserItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var userItems: List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class UserItemViewHolder(private val binding: UserListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(userItem: User){
            "${userItem.firstName} ${userItem.lastName}".also { binding.tvName.text = it }
            binding.tvEmail.text = userItem.email
        }
    }

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