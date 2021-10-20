package com.example.githubuserapp.ui.userdetail.fragment.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentInformationBinding
import com.example.githubuserapp.ui.userdetail.BottomNavDetailActivity
import com.example.githubuserapp.ui.userdetail.BottomNavDetailViewModel

class InformationFragment : Fragment() {

    private lateinit var viewModel: BottomNavDetailViewModel
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
        val root: View = binding.root

        showProgressBar(true)

        // Getting username value from MainActivity via BottomNavDetailActivity
        val username = activity?.intent?.getStringExtra(BottomNavDetailActivity.EXTRA_USERNAME)

        // View Model Declaration and Observer
        viewModel = ViewModelProvider(this).get(BottomNavDetailViewModel::class.java)
        viewModel.setUserDetailData(username)
        viewModel.getUserDetailData().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.apply {
                    showProgressBar(false)
                    tvName.text = user.name
                    tvUsername.text = user.login
                    tvFollowersValue.text = user.followers.toString()
                    tvFollowingValue.text = user.following.toString()
                    tvRepositoryValue.text = user.public_repos.toString()
                    tvBio.text = user.bio ?: "-"
                    tvCompany.text = user.company ?: "-"
                    tvLocation.text = user.location ?: "-"
                    tvEmail.text = user.email ?: "-"
                    tvTwitter.text = user.twitter_username ?: "-"
                    Glide.with(this@InformationFragment)
                        .load(user.avatar_url)
                        .placeholder(R.drawable.placeholder)
                        .into(ivAvatar)
                    Glide.with(this@InformationFragment)
                        .load(user.avatar_url)
                        .placeholder(R.drawable.placeholder)
                        .into(ivAvatarBg)
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}