package com.example.demoapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.adapters.UserItemAdapter
import com.example.demoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels{UserViewModel.Factory}

    private lateinit var userRecyclerView: RecyclerView
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

        userRecyclerView = binding.rvUserMain

        subscribeToObservers()
        setupRecyclerView()

        binding.fabDownloadMain.setOnClickListener { userViewModel.fetchAllUsers() }
        binding.fabClearMain.setOnClickListener { userViewModel.deleteAllUsers() }

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
        binding.rvUserMain.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}