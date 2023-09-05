package com.jjbaksa.jjbaksa.ui.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.post.PostContent
import com.jjbaksa.jjbaksa.databinding.ItemPostBinding
import com.jjbaksa.jjbaksa.util.setCalculateDate

class PostAdapter(
    val onClickItem: (PostContent) -> Unit
) : ListAdapter<PostContent, PostAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postContent: PostContent) {
            binding.postTitleTextView.text = postContent.title
            binding.postCreateTimeTextView.text = postContent.createdAt

            if (postContent.createdAt.setCalculateDate() >= 1) {
                binding.recentPostImageView.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onClickItem(postContent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : ItemCallback<PostContent>() {
            override fun areItemsTheSame(oldItem: PostContent, newItem: PostContent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostContent, newItem: PostContent): Boolean {
                return oldItem == newItem
            }
        }
    }
}
