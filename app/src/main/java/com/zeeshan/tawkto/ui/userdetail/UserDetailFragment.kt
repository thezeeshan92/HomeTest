package com.zeeshan.tawkto.ui.userdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.zeeshan.tawkto.models.UserDetail
import com.zeeshan.tawkto.musicplayer.R
import com.zeeshan.tawkto.musicplayer.databinding.UserDetailFragmentBinding
import com.zeeshan.tawkto.utils.Resource
import com.zeeshan.tawkto.utils.Resource.Status.SUCCESS
import com.zeeshan.tawkto.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var binding: UserDetailFragmentBinding by autoCleared()
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("username")?.let { viewModel.setUsername(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userDetailLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> {
                    binding.clUserDetail.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    bindCharacter(it.data!!)
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.clUserDetail.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindCharacter(userDetail: UserDetail) {
        binding.tvNameValue.text = userDetail.name
        binding.tvFollowers.text = "Followers:  ${userDetail.followers}"
        binding.tvFollowing.text = "Followings:  ${userDetail.following}"
        binding.tvCompanyName.text = userDetail.company
        binding.tvBlogLink.text = userDetail.blog
        binding.tvLocationName.text = userDetail.location
        Glide.with(binding.root)
            .load(userDetail.avatarUrl)
            .transform(CircleCrop())
            .into(binding.ivProfile)
    }
}