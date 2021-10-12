package com.example.githubuserapp.feature.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.feature.userdetail.UserDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GitHubUserApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        get() {
            val name = resources.getStringArray(R.array.name)
            val username = resources.getStringArray(R.array.username)
            val avatar = resources.obtainTypedArray(R.array.avatar)
            val company = resources.getStringArray(R.array.company)
            val location = resources.getStringArray(R.array.location)
            val repository = resources.getStringArray(R.array.repository)
            val followers = resources.getStringArray(R.array.followers)
            val following = resources.getStringArray(R.array.following)

            val listUser = ArrayList<User>()

            for (i in name.indices) {
                val user = User(
                    name[i],
                    username[i],
                    avatar.getResourceId(i, -1),
                    company[i],
                    location[i],
                    repository[i].toInt(),
                    followers[i].toInt(),
                    following[i].toInt()
                )
                listUser.add(user)
            }
            avatar.recycle()
            return listUser
        }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                showDetailUser(user)
            }
        })
    }

    private fun showDetailUser(user: User) {
        val detailUserIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
        detailUserIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(detailUserIntent)
    }
}