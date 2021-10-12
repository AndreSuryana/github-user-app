package com.example.githubuserapp.feature.userdetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var id: String

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "User Detail"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        // Calling getUserInfo function
        getUserInfo()

        // Event listener
        binding.btnVisit.setOnClickListener(this)
    }

    private fun getUserInfo() {
        // Getting user data as parcelable from main activity
        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        // Assign to components value
        binding.apply {
            user?.avatar?.let { ivAvatar.setImageResource(it) }
            user?.avatar?.let { ivAvatarBg.setImageResource(it) }
            tvName.text = user?.name
            tvUsername.text = user?.username
            tvRepositoryValue.text = user?.repository.toString()
            tvFollowersValue.text = user?.followers.toString()
            tvFollowingValue.text = user?.following.toString()
            tvCompany.text = user?.company
            tvLocation.text = user?.location
        }

        id = user?.username.toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_visit -> {
                goToGitHubPage(id)
            }
        }
    }

    private fun goToGitHubPage(id: String) {
        val gitHubIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/$id"))
        startActivity(gitHubIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}