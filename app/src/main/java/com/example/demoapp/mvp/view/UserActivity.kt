package com.example.demoapp.mvp.view

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.demoapp.UserList.User
import com.example.demoapp.databinding.ActivitySecondBinding
import com.example.demoapp.mvp.presenter.IUserPresenter
import com.example.demoapp.mvp.presenter.UserPresenterImpl
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity(), IUserView {
    private lateinit var binding: ActivitySecondBinding

    private lateinit var presenter: IUserPresenter

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

        presenter = UserPresenterImpl(this)
        binding.floatingActionButtonDownloadSecond.setOnClickListener { getAllItems() }
        binding.floatingActionButtonClearSecond.setOnClickListener { deleteAllItems() }
        binding.floatingActionButtonSwitchSecond.setOnClickListener { presenter.openMainActivity() }

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }


    override fun getContext():Context {
        return this
    }

    override fun getBinding(): ActivitySecondBinding {
        return binding
    }

    override fun getActivity(): AppCompatActivity {
        return this
    }

    override fun getAllItems() {
        lifecycleScope.launch { presenter.getAllUsers() }
    }

    override fun deleteAllItems() {
        lifecycleScope.launch { presenter.deleteAllUsers() }
    }

    override fun deleteItem(user: User) {
        lifecycleScope.launch { presenter.deleteUser(user) }
    }

}