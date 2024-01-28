package com.jjbaksa.jjbaksa.ui.follower

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowRequestCheckAdapter
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowerAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel


class FollowerFragment() : BaseFragment<FragmentFollowerBinding>() {

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followRequestCheckAdapter: FollowRequestCheckAdapter
    private lateinit var userAdapter: FollowerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var requestLinearLayoutManager: LinearLayoutManager
    private lateinit var userLinearLayoutManager: LinearLayoutManager
    private val viewModel: FollowerViewModel by activityViewModels()

    override val layoutId: Int
        get() = R.layout.fragment_follower

    override fun initView() {
        viewModel.getFollower(null, 10)
        viewModel.followRequestCheck(null, 10)
        followerAdapter = FollowerAdapter() {
            if (viewModel.unfollowedUsers.contains(it.account)) {
                viewModel.followRequest(it.account)
            } else {
                viewModel.followerDelete(it.account)
            }
        }
        followRequestCheckAdapter = FollowRequestCheckAdapter({
            viewModel.followRequestAccept(it.id)
        }) {
            viewModel.followRequestReject(it.id)
        }
        userAdapter = FollowerAdapter() {
            if (viewModel.unfollowedUsers.contains(it.account)) {
                viewModel.followRequest(it.account)
            } else {
                viewModel.followerDelete(it.account)
            }
        }

        linearLayoutManager = LinearLayoutManager(requireContext())
        requestLinearLayoutManager = LinearLayoutManager(requireContext())
        userLinearLayoutManager = LinearLayoutManager(requireContext())

        binding.followerRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = followerAdapter
        }
        binding.followRequestRecyclerView.apply {
            layoutManager = requestLinearLayoutManager
            adapter = followRequestCheckAdapter
        }
        binding.allUsersRecyclerView.apply {
            layoutManager = userLinearLayoutManager
            adapter = userAdapter
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
        binding.followRequestRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = requestLinearLayoutManager.itemCount
                    val lastPosition =
                        requestLinearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.followRequestHasMore.value == true) {
                        viewModel.followRequestHasMore.value = false
                        viewModel.followRequestCheck(
                            null,
                            10
                        )
                        binding.loadingView.setLoading(true)
                    }
                }
            })

        binding.allUsersRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = userLinearLayoutManager.itemCount
                    val lastPosition =
                        userLinearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 &&  lastPosition>= (itemCount - 1)) {
                        viewModel.getUserSearch(
                            viewModel.searchKeyword.value!!,
                            20,
                            userAdapter.currentList.last().id
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
                followerAdapter.submitList(followerAdapter.currentList+it.content)
            }
        }


        viewModel.followRequestList.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && followRequestCheckAdapter.currentList.isEmpty()) {
                binding.emptyContainer.isVisible = true
                followRequestCheckAdapter.submitList(emptyList())
            } else {
                binding.emptyContainer.isVisible = false
                followRequestCheckAdapter.submitList(followRequestCheckAdapter.currentList+it.content)
            }
        }

        viewModel.userList.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && userAdapter.currentList.isEmpty()) {
                binding.emptyContainer.isVisible = true
                userAdapter.submitList(emptyList())
            } else {
                binding.emptyContainer.isVisible = false

                userAdapter.submitList(userAdapter.currentList + it.content)

            }
        }


        viewModel.curser.observe(viewLifecycleOwner) {
            when (it) {
                UserCursor.ALL -> {
                    binding.followerRecyclerView.isVisible = false
                    binding.followRequestRecyclerView.isVisible = false
                    binding.allUsersRecyclerView.isVisible = true
                }
                UserCursor.FOLLOWER -> {
                    binding.followerRecyclerView.isVisible = true
                    binding.followRequestRecyclerView.isVisible = true
                    binding.allUsersRecyclerView.isVisible = false
                }
            }
        }
    }
}