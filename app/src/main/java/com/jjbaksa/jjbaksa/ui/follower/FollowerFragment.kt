package com.jjbaksa.jjbaksa.ui.follower

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowerAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel
import com.jjbaksa.jjbaksa.ui.inquiry.adapter.InquiryAllAdapter


class FollowerFragment() : BaseFragment<FragmentFollowerBinding>() {

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: FollowerViewModel by activityViewModels()

    override val layoutId: Int
        get() = R.layout.fragment_follower

    override fun initView() {
        viewModel.getFollower(null, 10)
        followerAdapter = FollowerAdapter() {
            if(viewModel.unfollowedUsers.contains(it.account)) {
                viewModel.followRequest(it.account)
            } else {
                viewModel.followerDelete(it.account)
            }
        }
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.followerRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = followerAdapter
        }
    }

    override fun initEvent() {
        binding.followerRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = linearLayoutManager.itemCount
                    val lastPosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.followerHasMore.value == true) {
                        viewModel.followerHasMore.value = false
                        viewModel.getFollower(
                            null,
                            10
                        )
                        binding.loadingView.setLoading(true)
                    }
                }
            })


    }

    override fun subscribe() {
        viewModel.followerList.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && followerAdapter.currentList.isEmpty()) {
                binding.emptyContainer.isVisible = true
                followerAdapter.submitList(emptyList())
            } else {
                binding.emptyContainer.isVisible = false
                followerAdapter.submitList(it.content)
            }
        }
    }
}