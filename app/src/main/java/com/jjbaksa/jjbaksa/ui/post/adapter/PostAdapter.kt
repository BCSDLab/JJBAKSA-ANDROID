package com.jjbaksa.jjbaksa.ui.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.post.Post
import com.jjbaksa.jjbaksa.databinding.ItemPostBinding
import com.jjbaksa.jjbaksa.util.setCalculateDate

class PostAdapter(
    val onClickItem: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.postTitleTextView.text = post.title
            binding.postCreateTimeTextView.text = post.createdAt

            if (post.createdAt.setCalculateDate() >= 1) {
                binding.recentPostImageView.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onClickItem(post)
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
        val diffUtil = object : ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
}
