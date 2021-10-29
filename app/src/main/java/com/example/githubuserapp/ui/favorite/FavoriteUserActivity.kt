package com.example.githubuserapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.example.githubuserapp.ui.userdetail.UserDetailActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideNoFavorite()
        showProgressBar()

        // Setup User Adapter
        userAdapter = UserAdapter()
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                Intent(this@FavoriteUserActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, user.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, user.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, user.avatarUrl)
                    startActivity(it)
                }
            }
        })

        // Setup View Model
        viewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

        // Setup Recycler View
        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = userAdapter
        }

        viewModel.getFavoriteUsers()?.observe(this, { list ->
            if (list != null) {
                val users = mapList(list)
                userAdapter.setListUsers(users)
                hideProgressBar()
            }
            if (list.isEmpty()) {
                showNoFavorite()
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val newUser = User(
                user.id,
                user.login,
                user.avatarUrl
            )
            listUsers.add(newUser)
        }
        return listUsers
    }

    private fun showNoFavorite() {
        binding.noFavorite.visibility = View.VISIBLE
    }

    private fun hideNoFavorite() {
        binding.noFavorite.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}