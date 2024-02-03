package com.jjbaksa.jjbaksa.ui.follower

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.jjbaksa.jjbaksa.R
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowRequestAdapter
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowerAdapter
import com.jjbaksa.jjbaksa.ui.follower.adapter.RecentlyActiveAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerActivity : BaseActivity<ActivityFollowerBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_follower

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followRequestAdapter: FollowRequestAdapter
    private lateinit var userAdapter: FollowerAdapter
    private lateinit var recentlyActiveAdapter: RecentlyActiveAdapter
    private val viewModel: FollowerViewModel by viewModels()

    override fun initView() {
        binding.lifecycleOwner = this
        viewModel.getFollower(null, 20)
        viewModel.getBeRequestedFollowers(null, 20)
        viewModel.getRequestedFollowers(null, 20)
        viewModel.getRecentlyActiveFollowers(20, null)

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

        recentlyActiveAdapter = RecentlyActiveAdapter()

        binding.rvAllFollower.adapter = followerAdapter
        binding.rvRequestFollow.adapter = followRequestAdapter
        binding.rvSearchResult.adapter = userAdapter
        binding.rvRecentlyActiveFollower.adapter = recentlyActiveAdapter
    }

    override fun subscribe() {
        binding.rvAllFollower.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = binding.rvAllFollower.layoutManager?.itemCount ?: 0
                    val lastPosition =
                        binding.rvAllFollower.layoutManager?.let { it as LinearLayoutManager }
                            ?.findLastCompletelyVisibleItemPosition() ?: 0

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
                    val itemCount = binding.rvRequestFollow.layoutManager?.itemCount ?: 0
                    val lastPosition =
                        binding.rvAllFollower.layoutManager?.let { it as LinearLayoutManager }
                            ?.findLastCompletelyVisibleItemPosition() ?: 0

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.receivedFollowRequestHasMore.value == true && viewModel.sendFollowRequestHasMore.value == true) {
                        viewModel.receivedFollowRequestHasMore.value = false
                        viewModel.sendFollowRequestHasMore.value = false
                        viewModel.getBeRequestedFollowers(
                            null,
                            20
                        )
                        viewModel.getRequestedFollowers(
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
                    val itemCount = binding.rvSearchResult.layoutManager?.itemCount ?: 0
                    val lastPosition =
                        binding.rvAllFollower.layoutManager?.let { it as LinearLayoutManager }
                            ?.findLastCompletelyVisibleItemPosition() ?: 0

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

        binding.rvRecentlyActiveFollower.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = binding.rvRecentlyActiveFollower.layoutManager?.itemCount ?: 0
                    val lastPosition =
                        binding.rvAllFollower.layoutManager?.let { it as LinearLayoutManager }
                            ?.findLastCompletelyVisibleItemPosition() ?: 0

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.recentlyActiveHasMore.value == true) {
                        viewModel.recentlyActiveHasMore.value = false
                        viewModel.getRecentlyActiveFollowers(
                            20,
                            recentlyActiveAdapter.currentList.last().id
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
                    viewModel.getUserSearch(it.toString(), 20, null)
                    viewModel.cursor.value = UserCursor.ALL
                }
            }
        }

        viewModel.followerList.observe(this) {
            binding.loadingView.setLoading(false)
            followerAdapter.submitList(followerAdapter.currentList + it.content)


        }

        viewModel.beRequestedFollowers.observe(this) {
            binding.loadingView.setLoading(false)
            followRequestAdapter.submitList(followRequestAdapter.currentList + it.content)
        }

        viewModel.requestFollowers.observe(this) {
            binding.loadingView.setLoading(false)
            followRequestAdapter.submitList(followRequestAdapter.currentList + it.content)
        }

        viewModel.userList.observe(this) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && userAdapter.currentList.isEmpty()) {
                userAdapter.submitList(emptyList())
            } else {
                userAdapter.submitList(userAdapter.currentList + it.content)
            }
        }

        viewModel.recentlyActiveList.observe(this) {
            binding.loadingView.setLoading(false)
            recentlyActiveAdapter.submitList(recentlyActiveAdapter.currentList + it.content)
        }

        viewModel.cursor.observe(this) {
            when (it) {
                UserCursor.ALL -> {
                    binding.rvRecentlyActiveFollower.visibility = View.GONE
                    binding.clAllFollower.visibility = View.GONE
                    binding.clRequestFollow.visibility = View.GONE
                    binding.clSearchResult.visibility = View.VISIBLE
                }

                UserCursor.FOLLOWER -> {
                    binding.rvRecentlyActiveFollower.visibility = View.VISIBLE
                    binding.clAllFollower.visibility = View.VISIBLE
                    binding.clRequestFollow.visibility = View.VISIBLE
                    binding.clSearchResult.visibility = View.GONE
                }
            }
        }

        binding.clRecentlyActiveFollower.setOnClickListener {
            expandView(binding.rvRecentlyActiveFollower, binding.ivRecentlyActiveExpand)
        }
        binding.clAllFollower.setOnClickListener {
            expandView(binding.rvAllFollower, binding.ivAllFollowerExpand)
        }
        binding.clRequestFollow.setOnClickListener {
            expandView(binding.rvRequestFollow, binding.ivRequestFollowExpand)
        }
        binding.clSearchResult.setOnClickListener {
            expandView(binding.rvSearchResult, binding.ivSearchExpand)
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

    private fun expandView(view: View, ivExpand: ImageView) {
        if (view.visibility == View.VISIBLE) {
            view.visibility = View.GONE
            ivExpand.setImageResource(R.drawable.sel_jj_check_box_more_info_opened)
        } else {
            view.visibility = View.VISIBLE
            ivExpand.setImageResource(R.drawable.sel_jj_check_box_more_info_closed)
        }
    }
}
