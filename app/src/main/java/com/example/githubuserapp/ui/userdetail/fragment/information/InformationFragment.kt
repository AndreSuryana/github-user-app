package com.example.githubuserapp.ui.userdetail.fragment.information

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

        showProgressBar()

        // Getting username value from MainActivity via BottomNavDetailActivity
        val username = activity?.intent?.getStringExtra(BottomNavDetailActivity.EXTRA_USERNAME)

        // View Model Declaration and Observer
        viewModel = ViewModelProvider(this).get(BottomNavDetailViewModel::class.java)
        viewModel.setUserDetailData(username)
        viewModel.getUserDetailData().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.apply {
                    hideProgressBar()
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
                    ivAvatar.loadImage(user.avatar_url)
                    ivAvatarBg.loadImage(user.avatar_url)
                }
            }
        })

        return root
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