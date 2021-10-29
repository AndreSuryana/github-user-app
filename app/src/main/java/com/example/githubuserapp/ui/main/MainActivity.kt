package com.example.githubuserapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.prefs.SettingPreferences
import com.example.githubuserapp.ui.favorite.FavoriteUserActivity
import com.example.githubuserapp.ui.setting.SettingActivity
import com.example.githubuserapp.ui.setting.SettingViewModel
import com.example.githubuserapp.ui.setting.ViewModelFactory
import com.example.githubuserapp.ui.userdetail.UserDetailActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_GitHubUserApp)
        setContentView(binding.root)

        // Load latest preferences if exists
        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)
        settingViewModel.getThemeSettings().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        // Show Not Found before data was loaded
        showNotFound()

        // Adapter Setup
        userAdapter = UserAdapter()
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, user.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, user.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, user.avatarUrl)
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

        // Button Favorite
        binding.btnFavorite.setOnClickListener {
            Intent(this@MainActivity, FavoriteUserActivity::class.java).also {
                startActivity(it)
            }
        }

        // Button Setting
        binding.btnSetting.setOnClickListener {
            Intent(this@MainActivity, SettingActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideNotFound()
    }

    private fun searchUser() {
        binding.svSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                hideNotFound()
                showProgressBar()
                viewModel.setSearchUsersResult(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // View Model Observer
        viewModel.getSearchUserResult().observe(this, { users ->
            if (users != null) {
                userAdapter.setListUsers(users)
                hideProgressBar()
            }
            if (users.size == 0) {
                showNotFound()
            }
        })

        viewModel.status.observe(this, { status ->
            // If status false, then it means error
            if (!status) {
                hideProgressBar()
                showNotFound()
            }
        })
    }

    private fun showNotFound() {
        binding.notFound.visibility = View.VISIBLE
    }

    private fun hideNotFound() {
        binding.notFound.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}