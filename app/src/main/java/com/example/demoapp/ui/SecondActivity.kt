package com.example.demoapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.contracts.SecondActivityContract
import com.example.demoapp.data.remote.ReqresApiClient
import com.example.demoapp.databinding.ActivitySecondBinding
import com.example.demoapp.model.SecondModel
import com.example.demoapp.presenter.SecondPresenter
import com.example.demoapp.repositories.UserDataStoreRepository

class SecondActivity : AppCompatActivity(), SecondActivityContract.View {
    private lateinit var binding: ActivitySecondBinding

    private lateinit var presenter: SecondPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter = SecondPresenter(SecondModel(UserDataStoreRepository(this), ReqresApiClient.reqresApiService), binding, this, itemTouchCallback)

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
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
            val user = presenter.userAdapter.userItems[pos]
            presenter.deleteUser(user)
        }
    }

}