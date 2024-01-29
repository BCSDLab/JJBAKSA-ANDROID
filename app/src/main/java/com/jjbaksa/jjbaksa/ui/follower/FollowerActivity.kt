package com.jjbaksa.jjbaksa.ui.follower

import android.content.Intent
import com.jjbaksa.jjbaksa.R
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowRequestAdapter
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowerAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerActivity : BaseActivity<ActivityFollowerBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_follower

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followRequestAdapter: FollowRequestAdapter
    private lateinit var userAdapter: FollowerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var requestLinearLayoutManager: LinearLayoutManager
    private lateinit var userLinearLayoutManager: LinearLayoutManager
    private lateinit var followRequestLinearLayoutManager: LinearLayoutManager
    private val viewModel: FollowerViewModel by viewModels()

    override fun initView() {
        binding.lifecycleOwner = this
        viewModel.getFollower(null, 20)
        viewModel.followRequestReceived(null, 20)
        viewModel.followRequestSend(null, 20)

        followerAdapter = FollowerAdapter({
            toggleFollow(it)
        }) {
            goToFollowerActivity(it)
        }
        followRequestAdapter = FollowRequestAdapter({
            viewModel.followRequestAccept(it.id)
        }) {
            viewModel.followRequestReject(it.id)
        }

        userAdapter = FollowerAdapter({
            toggleFollow(it)
        }) {
            goToFollowerActivity(it)
        }

        linearLayoutManager = LinearLayoutManager(this)
        requestLinearLayoutManager = LinearLayoutManager(this)
        userLinearLayoutManager = LinearLayoutManager(this)
        followRequestLinearLayoutManager = LinearLayoutManager(this)

        binding.rvAllFollower.apply {
            layoutManager = linearLayoutManager
            adapter = followerAdapter
        }
        binding.rvRequestFollow.apply {
            layoutManager = requestLinearLayoutManager
            adapter = followRequestAdapter
        }

        binding.rvSearchResult.apply {
            layoutManager = userLinearLayoutManager
            adapter = userAdapter
        }
    }

    override fun subscribe() {
        binding.rvAllFollower.addOnScrollListener(
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

        binding.rvRequestFollow.addOnScrollListener(
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

        binding.rvSearchResult.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = userLinearLayoutManager.itemCount
                    val lastPosition =
                        userLinearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1)) {
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

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }

        binding.ivSearch.setOnClickListener {

            binding.etSearch.text?.let {
                if (it.isEmpty()) {
                    showSnackBar(getString(R.string.main_page_search_edit_text_hint))
                    viewModel.cursor.value = UserCursor.FOLLOWER
                } else {
                    viewModel.getUserSearch(it.toString(), 20, null )
                    viewModel.cursor.value = UserCursor.ALL
                }
            }
        }

        viewModel.followerList.observe(this) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && followerAdapter.currentList.isEmpty()) {
                followerAdapter.submitList(emptyList())
            } else {
                followerAdapter.submitList(followerAdapter.currentList + it.content)
            }
        }


        viewModel.receivedFollowRequestList.observe(this) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && followRequestAdapter.currentList.isEmpty()) {
                followRequestAdapter.submitList(emptyList())
            } else {
                followRequestAdapter.submitList(followRequestAdapter.currentList + it.content)
            }
        }


        viewModel.sendFollowRequestList.observe(this) {
            binding.loadingView.setLoading(false)
            TODO()
        }

        viewModel.userList.observe(this) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && userAdapter.currentList.isEmpty()) {
                userAdapter.submitList(emptyList())
            } else {
                userAdapter.submitList(userAdapter.currentList + it.content)
            }
        }

        viewModel.cursor.observe(this) {
            when (it) {
                UserCursor.ALL -> {
                    binding.rvAllFollower.isVisible = false
                    binding.rvRequestFollow.isVisible = false
                    binding.rvSearchResult.isVisible = true
                }

                UserCursor.FOLLOWER -> {
                    binding.rvAllFollower.isVisible = true
                    binding.rvRequestFollow.isVisible = true
                    binding.rvSearchResult.isVisible = false
                }
            }
        }
    }

    private fun toggleFollow(user: User) {
        if (viewModel.unfollowedUsers.contains(user.account)) {
            viewModel.followRequest(user.account)
        } else {
            viewModel.followerDelete(user.account)
        }
    }

    private fun goToFollowerActivity(user: User) {
        val intent = Intent(this, FollowerProfileActivity::class.java).apply {
            putExtra("nickname", user.nickname)
            putExtra("account", user.account)
            putExtra("fid", user.id)
            putExtra("profileImage", user.profileImage.url)
            putExtra("followerCount", user.userCountResponse.friendCount)
        }
        startActivity(intent)
    }
}
