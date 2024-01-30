package com.jjbaksa.jjbaksa.ui.follower.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.domain.model.follower.FollowContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemFollowBinding

class FollowRequestAdapter(
    private val onAcceptClicked: (FollowContent) -> Unit,
    private val onDeleteClicked: (FollowContent) -> Unit,
) : ListAdapter<FollowContent, FollowRequestAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowContent) {
            binding.followNameTextView.text = item.user?.nickname
            binding.followAccountTextView.text = "@" + item.user?.account
            Glide.with(binding.root.context)
                .load(item.user.profileImage.url)
                .error(R.drawable.baseline_supervised_user_circle_24)
                .circleCrop()
                .into(binding.ivProfile)

            binding.followButton.isVisible = false
            binding.acceptButton.isVisible = true
            binding.deleteButton.isVisible = true

            binding.acceptButton.setOnClickListener {
                onAcceptClicked(item)
            }
            binding.deleteButton.setOnClickListener {
                onDeleteClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFollowBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<FollowContent>() {

            override fun areItemsTheSame(oldItem: FollowContent, newItem: FollowContent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowContent,
                newItem: FollowContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
