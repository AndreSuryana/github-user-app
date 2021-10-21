package com.example.githubuserapp.ui.userdetail.fragment.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.ui.userdetail.BottomNavDetailActivity

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private lateinit var viewModel: FollowingViewModel
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
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter Setup
        userAdapter = UserAdapter()
        // userAdapter.notifyDataSetChanged()

        // View Model Setup
        viewModel =
            ViewModelProvider(this).get(FollowingViewModel::class.java)

        // Recycler View Setup
        binding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = userAdapter
        }

        // Show progress bar, while getting data from api
        showProgressBar()

        // Get username value from MainActivity
        username =
            activity?.intent?.getStringExtra(BottomNavDetailActivity.EXTRA_USERNAME).toString()

        // View Model
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, { users ->
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
        binding.noFollowing.visibility = View.VISIBLE
    }

    private fun hideEmptyData() {
        binding.noFollowing.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}