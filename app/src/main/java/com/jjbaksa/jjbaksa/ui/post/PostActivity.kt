package com.jjbaksa.jjbaksa.ui.post

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.model.post.PostContent
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
    private val postAdapter: PostAdapter by lazy {
        PostAdapter(::onClickPostItem)
    }

    private val startPostDetail =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        }

    override fun initView() {
        binding.postRecyclerView.also {
            it.adapter = postAdapter
            it.layoutManager = LinearLayoutManager(this)
        }
        viewModel.getPost(null, null, 20)
    }

    override fun subscribe() {
        viewModel.post.observe(this) {
            postAdapter.submitList(it.content)
        }
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
    }

    private fun onClickPostItem(postContent: PostContent) {
        val intent = Intent(this, PostDetailActivity::class.java).apply {
            putExtra("post_id", postContent.id)
        }
        startPostDetail.launch(intent)
    }
}
