package com.example.demoapp.mvp.presenter

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.UserList
import com.example.demoapp.adapters.UserItemAdapter
import com.example.demoapp.data.remote.UserApiClient
import com.example.demoapp.mvp.model.UserModelImpl
import com.example.demoapp.mvp.model.RemoteServerEvent
import com.example.demoapp.mvp.view.IUserView
import com.example.demoapp.mvvm.ui.MainActivity

class UserPresenterImpl (
    private val view: IUserView,
) : IUserPresenter{
    private val model = UserModelImpl(view.getContext(), RemoteServerEvent(UserApiClient.userApiService))
    private var userList: LiveData<UserList> = model.observeAllUsers().asLiveData()
    private lateinit var userAdapter: UserItemAdapter

    init {
        subscribeToObservers()
        setupRecyclerView()
    }
    override suspend fun getAllUsers() {
        model.fetchAllUsersFromRemote()
    }

    override suspend fun deleteUser(user: UserList.User) {
        model.deleteUserFromDatastore(user)
    }

    override suspend fun deleteAllUsers() {
        model.deleteAllUsersFromDatastore()
    }

    override fun openMainActivity() {
        val intent = Intent(view.getActivity(), MainActivity::class.java)
        startActivity(view.getActivity(),intent,null)
    }

    override fun onDestroy() {
    }


    private fun subscribeToObservers() {
        userList.observe(view.getActivity()) {
            userAdapter.userItems = it.usersList
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserItemAdapter()
        view.getBinding().recyclerViewUserSecond.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = true

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.layoutPosition
                    val user = userAdapter.userItems[pos]
                    view.deleteItem(user)
                }
            }).attachToRecyclerView(this)
        }
    }


}