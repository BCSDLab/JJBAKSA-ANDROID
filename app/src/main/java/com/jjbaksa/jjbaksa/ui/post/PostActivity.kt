package com.jjbaksa.jjbaksa.ui.post

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.resp.post.Post
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPostBinding
import com.jjbaksa.jjbaksa.ui.post.adapter.PostAdapter
import com.jjbaksa.jjbaksa.ui.post.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : BaseActivity<ActivityPostBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_post
    private val viewModel: PostViewModel by viewModels()
    private lateinit var adapter: PostAdapter

    private val startPostDetail =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        }

    override fun initView() {
        adapter = PostAdapter(::onClickPostItem)
        binding.postRecyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }
        viewModel.getPost("", "", 20)
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.post.observe(this) {
            adapter.submitList(it.content)
        }
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
    }

    private fun onClickPostItem(post: Post) {
        val intent = Intent(this, PostDetailActivity::class.java).apply {
            putExtra("post_id", post.id)
        }
        startPostDetail.launch(intent)
    }
}
