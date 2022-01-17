package com.zeeshan.tawkto.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeeshan.tawkto.adapter.UserAdapter
import com.zeeshan.tawkto.musicplayer.R
import com.zeeshan.tawkto.musicplayer.databinding.UserFragmentBinding
import com.zeeshan.tawkto.utils.Resource.Status.*
import com.zeeshan.tawkto.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment(), UserAdapter.UserItemListener {


    private var binding: UserFragmentBinding by autoCleared()
    private val viewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    var nextSince = 0
    var isNewCreateFrag = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UserAdapter(this)
        binding.apply {
            binding.rvUsers.apply {
                adapter = userAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                addOnScrollListener(this@UserFragment.scrollListener)
            }
        }
        binding.etSearch.addTextChangedListener {
            userAdapter.filter.filter(it)
        }
        setupObservers()
        if (isNewCreateFrag) {
            viewModel.setSince(nextSince)
            isNewCreateFrag = false
        }
    }


    private fun setupObservers() {
        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvUsers.visibility = View.VISIBLE
                    if (!it.data.isNullOrEmpty()) {
                        userAdapter.addUserItem(ArrayList(it.data))
                        nextSince = it.data.last().id;
                    } else
                        isLastPage = true
                }
                ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) { //State is scrolling
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val totalVisibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastItem =
                    firstVisibleItemPosition + totalVisibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val shouldPaginate =
                    isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isScrolling

                if (shouldPaginate) {
                    isScrolling = false
                    viewModel.setSince(nextSince)
                }
            }
        }
    }

    override fun onUserClicked(username: String) {
        findNavController().navigate(
            R.id.action_userFragment_to_userDetailFragment,
            bundleOf("username" to username)
        )

    }
}