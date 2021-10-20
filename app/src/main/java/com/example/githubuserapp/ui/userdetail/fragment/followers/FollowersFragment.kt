package com.example.githubuserapp.ui.userdetail.fragment.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.ui.userdetail.BottomNavDetailActivity

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
        val root: View = binding.root

        // Adapter Setup
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

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
        showProgressBar(true)

        // Get username value from MainActivity
        username =
            activity?.intent?.getStringExtra(BottomNavDetailActivity.EXTRA_USERNAME).toString()

        // View Model
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                userAdapter.setListUsers(users)
                showProgressBar(false)
                if (users.size == 0) {
                    showEmptyData(true)
                } else {
                    showEmptyData(false)
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

    private fun showEmptyData(isLoading: Boolean) {
        if (isLoading) {
            binding.noFollowers.visibility = View.VISIBLE
        } else {
            binding.noFollowers.visibility = View.GONE
        }
    }
}