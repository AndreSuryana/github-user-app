package com.example.githubuserapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.userdetail.BottomNavDetailActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GitHubUserApp)
        setContentView(binding.root)

        // Show Not Found Fragment before data was loaded
        showNotFoundFragment()

        // Adapter Setup
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                Intent(this@MainActivity, BottomNavDetailActivity::class.java).also {
                    it.putExtra(BottomNavDetailActivity.EXTRA_USERNAME, user.login)
                    startActivity(it)
                }
            }

        })

        // View Model Declaration
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        // Recycler View Setup
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = userAdapter
        }

        // Execute when user submit on SearchView
        searchUser()
    }

    override fun onPause() {
        super.onPause()
        hideNotFoundFragment()
    }

    private fun searchUser() {
        binding.svSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                hideNotFoundFragment()
                showProgressBar()
                viewModel.setSearchUsersResult(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Observer
        viewModel.getSearchUserResult().observe(this, {
            if (it != null) {
                userAdapter.setListUsers(it)
                hideProgressBar()
            }
            if (it.size == 0) {
                showNotFoundFragment()
            }
        })
    }

    private fun showNotFoundFragment() {
        binding.notFound.visibility = View.VISIBLE
    }

    private fun hideNotFoundFragment() {
        binding.notFound.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}