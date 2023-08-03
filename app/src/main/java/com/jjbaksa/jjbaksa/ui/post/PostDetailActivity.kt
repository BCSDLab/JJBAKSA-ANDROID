package com.jjbaksa.jjbaksa.ui.post

import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPostDetailBinding
import com.jjbaksa.jjbaksa.ui.post.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_post_detail
    private val viewModel: PostViewModel by viewModels()

    override fun initView() {
        val id = intent.getIntExtra("post_id", 0)
        viewModel.getPostDetail(id)
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.postDetail.observe(this) {
            binding.postDetailTitleTextView.text = it.title
            binding.postDetailCreateTimeTextView.text = it.createdAt
            binding.postDetailContentTextView.text = it.content
        }
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
    }
}
