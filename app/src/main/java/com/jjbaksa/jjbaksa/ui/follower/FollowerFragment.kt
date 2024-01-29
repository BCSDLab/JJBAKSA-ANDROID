package com.jjbaksa.jjbaksa.ui.follower

import android.content.Intent
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowRequestAdapter
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowerAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment() : BaseFragment<FragmentFollowerBinding>() {

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followRequestAdapter: FollowRequestAdapter
    private lateinit var userAdapter: FollowerAdapter
    private lateinit var sendRequestAdapter: FollowRequestAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var requestLinearLayoutManager: LinearLayoutManager
    private lateinit var userLinearLayoutManager: LinearLayoutManager
    private lateinit var followRequestLinearLayoutManager: LinearLayoutManager
    private val viewModel: FollowerViewModel by activityViewModels()

    override val layoutId: Int
        get() = R.layout.fragment_follower

    override fun initView() {
        viewModel.getFollower(null, 20)
        viewModel.followRequestReceived(null, 20)
        viewModel.followRequestSend(null,20)

        followerAdapter = FollowerAdapter({
            if (viewModel.unfollowedUsers.contains(it.account)) {
                viewModel.followRequest(it.account)
            } else {
                viewModel.followerDelete(it.account)
            }
        }) {
            goToFollowerActivity(it)
        }
        followRequestAdapter = FollowRequestAdapter({
            viewModel.followRequestAccept(it.id)
        }) {
            viewModel.followRequestReject(it.id)
        }

        sendRequestAdapter = FollowRequestAdapter({
                viewModel.followRequestAccept(it.id)
            } ) {
                viewModel.followRequestAccept(it.id)
        }

        userAdapter = FollowerAdapter({
            if (viewModel.unfollowedUsers.contains(it.account)) {

                viewModel.followRequest(it.account)
            } else {
                viewModel.followerDelete(it.account)
            }
        }) {
            goToFollowerActivity(it)
        }

        linearLayoutManager = LinearLayoutManager(requireContext())
        requestLinearLayoutManager = LinearLayoutManager(requireContext())
        userLinearLayoutManager = LinearLayoutManager(requireContext())
        followRequestLinearLayoutManager = LinearLayoutManager(requireContext())

        binding.followerRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = followerAdapter
        }
        binding.recivedFollowRequestRecyclerView.apply {
            layoutManager = requestLinearLayoutManager
            adapter = followRequestAdapter
        }

        binding.sendFollowRequestRecyclerView.apply {
            layoutManager = followRequestLinearLayoutManager
            adapter = sendRequestAdapter
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
                            20
                        )
                        binding.loadingView.setLoading(true)

                    }
                }
            })

        binding.recivedFollowRequestRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = requestLinearLayoutManager.itemCount
                    val lastPosition =
                        requestLinearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.receivedFollowRequestHasMore.value == true) {
                        viewModel.receivedFollowRequestHasMore.value = false
                        viewModel.followRequestReceived(
                            null,
                            20
                        )
                        binding.loadingView.setLoading(true)
                    }
                }
            })

        binding.sendFollowRequestRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = followRequestLinearLayoutManager.itemCount
                    val lastPosition =
                        followRequestLinearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.sendFollowRequestHasMore.value == true) {
                        viewModel.sendFollowRequestHasMore.value = false
                        viewModel.followRequestSend(
                            null,
                            20
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
                followerAdapter.submitList(emptyList())
            } else {
                followerAdapter.submitList(followerAdapter.currentList+it.content)
            }
        }


        viewModel.receivedFollowRequestList.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && followRequestAdapter.currentList.isEmpty()) {
                followRequestAdapter.submitList(emptyList())
            } else {
                followRequestAdapter.submitList(followRequestAdapter.currentList+it.content)
            }
        }


        viewModel.sendFollowRequestList.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && sendRequestAdapter.currentList.isEmpty()) {
                sendRequestAdapter.submitList(emptyList())
            } else {
                sendRequestAdapter.submitList(sendRequestAdapter.currentList+it.content)
            }
        }

        viewModel.userList.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && userAdapter.currentList.isEmpty()) {
                userAdapter.submitList(emptyList())
            } else {
                userAdapter.submitList(userAdapter.currentList + it.content)
            }
        }




        viewModel.cursor.observe(viewLifecycleOwner) {
            when (it) {
                UserCursor.ALL -> {
                    binding.followerRecyclerView.isVisible = false
                    binding.recivedFollowRequestRecyclerView.isVisible = false
                    binding.sendFollowRequestRecyclerView.isVisible = false
                    binding.allUsersRecyclerView.isVisible = true
                }
                UserCursor.FOLLOWER -> {
                    binding.followerRecyclerView.isVisible = true
                    binding.recivedFollowRequestRecyclerView.isVisible = true
                    binding.sendFollowRequestRecyclerView.isVisible = true
                    binding.allUsersRecyclerView.isVisible = false
                }
            }
        }
    }
    private fun goToFollowerActivity(user: User) {
        val intent = Intent(requireContext(), FollowerProfileActivity::class.java).apply {
            putExtra("nickname", user.nickname)
            putExtra("account", user.account)
            putExtra("fid", user.id)
            putExtra("profileImage", user.profileImage.url)
            putExtra("followerCount", user.userCountResponse.friendCount)
        }
        startActivity(intent)
    }
}