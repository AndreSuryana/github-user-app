package com.example.githubuserapp.ui.userdetail.fragment.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentInformationBinding
import com.example.githubuserapp.ui.userdetail.UserDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InformationFragment : Fragment() {

    private lateinit var viewModel: InformationViewModel
    private var _binding: FragmentInformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // View Binding Setup
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressBar()

        // Getting username value from MainActivity via BottomNavDetailActivity
        val username = activity?.intent?.getStringExtra(UserDetailActivity.EXTRA_USERNAME)
        val id = activity?.intent?.getIntExtra(UserDetailActivity.EXTRA_ID, 0)
        val avatarUrl = activity?.intent?.getStringExtra(UserDetailActivity.EXTRA_AVATAR)

        // View Model Declaration and Observer
        viewModel = ViewModelProvider(this).get(InformationViewModel::class.java)
        viewModel.setUserDetailData(username)
        viewModel.getUserDetailData().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.apply {
                    hideProgressBar()
                    tvName.text = user.name
                    tvUsername.text = user.login
                    tvFollowersValue.text = user.followers.toString()
                    tvFollowingValue.text = user.following.toString()
                    tvRepositoryValue.text = user.publicRepos.toString()
                    tvBio.text = user.bio ?: "-"
                    tvCompany.text = user.company ?: "-"
                    tvLocation.text = user.location ?: "-"
                    tvEmail.text = user.email ?: "-"
                    tvTwitter.text = user.twitterUsername ?: "-"
                    ivAvatar.loadImage(user.avatarUrl)
                    ivAvatarBg.loadImage(user.avatarUrl)
                }
            }
        })

        // Setup Toggle Button (Favorite)
        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavoriteUsers(id)
            withContext(Dispatchers.Main) {
                if (count != null && count > 0) {
                    binding.toggleFavorite.isChecked = true
                    checked = true
                } else {
                    binding.toggleFavorite.isChecked = false
                    checked = false
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            checked = !checked
            CoroutineScope(Dispatchers.IO).launch {
                if (checked) {
                    viewModel.addFavoriteUser(id, username, avatarUrl)
                } else {
                    viewModel.deleteFavoriteUser(id)
                }
            }
        }

        // Setup Share Button
        binding.btnShare.setOnClickListener {
            val caption = "I found this user on GitHub\n" +
                    "https://www.github.com/$username\n" +
                    "If you interested, please visit official page from the link above!"

            Intent().also {
                it.action = Intent.ACTION_SEND
                it.type = "text/plain"
                it.putExtra(Intent.EXTRA_TEXT, caption)
                it.putExtra(Intent.EXTRA_SUBJECT, "GitHub User App")
                startActivity(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(this)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}