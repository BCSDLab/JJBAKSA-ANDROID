package com.jjbaksa.jjbaksa.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPostBinding
import com.jjbaksa.jjbaksa.databinding.ActivityPostDetailBinding
import com.jjbaksa.jjbaksa.ui.post.viewmodel.PostViewModel

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_post_detail
    private val viewModel: PostViewModel by viewModels()

    override fun initView() {
        val id = intent.getStringExtra("post_id")
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
    }
}