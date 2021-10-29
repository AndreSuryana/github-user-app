package com.example.githubuserapp.ui.userdetail.fragment.followers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.ui.userdetail.UserDetailActivity

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private lateinit var viewModel: FollowersViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get username value from MainActivity
        username =
            activity?.intent?.getStringExtra(UserDetailActivity.EXTRA_USERNAME).toString()

        // Adapter Setup
        userAdapter = UserAdapter()
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                Intent(activity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, user.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, user.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, user.avatarUrl)
                    startActivity(it)
                }
            }
        })

        // View Model Setup
        viewModel =
            ViewModelProvider(this).get(FollowersViewModel::class.java)

        // Recycler View Setup
        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = userAdapter
        }

        // Show progress bar, while getting data from api
        showProgressBar()

        // View Model
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                userAdapter.setListUsers(users)
                hideProgressBar()
                if (users.size == 0) {
                    showEmptyData()
                } else {
                    hideEmptyData()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showEmptyData() {
        binding.noFollowers.visibility = View.VISIBLE
    }

    private fun hideEmptyData() {
        binding.noFollowers.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}