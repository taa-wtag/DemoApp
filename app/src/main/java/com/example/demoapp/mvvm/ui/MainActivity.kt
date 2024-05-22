package com.example.demoapp.mvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.adapters.UserItemAdapter
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.mvp.view.SecondActivity
import com.example.demoapp.mvvm.repositories.UserDataStoreRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val factory = UserViewModel.UserViewModelFactory(UserDataStoreRepository(this))
        userViewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)

        subscribeToObservers()
        setupRecyclerView()

        binding.floatingActionButtonDownloadMain.setOnClickListener { userViewModel.fetchAllUsers() }
        binding.floatingActionButtonClearMain.setOnClickListener { userViewModel.deleteAllUsers() }
        binding.floatingActionButtonSwitchMain.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        }

    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
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
            userViewModel.deleteUser(user)
        }
    }

    private fun subscribeToObservers() {
        userViewModel.userList.observe(this) {
            userAdapter.userItems = it.usersList
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserItemAdapter()
        binding.recyclerViewUserMain.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}