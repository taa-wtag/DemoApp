package com.example.demoapp.mvp.presenter

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapp.UserList
import com.example.demoapp.adapters.UserItemAdapter
import com.example.demoapp.mvp.contracts.SecondActivityContract
import com.example.demoapp.databinding.ActivitySecondBinding
import com.example.demoapp.mvvm.ui.MainActivity
import com.example.demoapp.mvp.ui.SecondActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SecondPresenter (
    private val model: SecondActivityContract.Model,
    private val binding: ActivitySecondBinding,
    private val activity: SecondActivity,
    private val itemTouchCallback: ItemTouchHelper.SimpleCallback
) : SecondActivityContract.Presenter , SecondActivityContract.Model.OnFinishListener{
    private val scope = CoroutineScope(Dispatchers.IO)
    lateinit var userAdapter: UserItemAdapter
    private var userList: LiveData<UserList> = model.observeAllUsers().asLiveData()

    init {
        binding.fabDownloadSecond.setOnClickListener { getAllUsers() }
        binding.fabClearSecond.setOnClickListener { deleteAllUsers() }
        binding.fabSwitchSecond.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(activity,intent,null)
        }

        subscribeToObservers()
        setupRecyclerView()
    }
    override fun getAllUsers() {
        scope.launch{ model.fetchAllUsersFromRemote() }
    }

    override fun deleteUser(user: UserList.User) {
        scope.launch { model.deleteUser(user) }
    }

    override fun deleteAllUsers() {
        scope.launch { model.deleteAllUsers() }
    }

    override fun onDestroy() {
        scope.cancel()
    }

    override fun onLoading() {
    }

    override fun onError(message: String) {
    }

    override fun onSuccess(list: UserList) {
    }



    private fun subscribeToObservers() {
        userList.observe(activity) {
            userAdapter.userItems = it.usersList
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserItemAdapter()
        binding.rvUserSecond.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

}