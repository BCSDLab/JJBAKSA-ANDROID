package com.jjbaksa.jjbaksa.ui.follower.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.jjbaksa.databinding.ItemFollowBinding

class FollowerAdapter(
    private val onButtonClicked: (User) -> Unit,
    private val onItemClicked: (User) -> Unit
) : ListAdapter<User, FollowerAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.followNameTextView.text = item.nickname
            binding.followAccountTextView.text = "@" +item.account

            binding.followingButton.setOnClickListener {
                onButtonClicked(item)
                binding.followingButton.isVisible = false
                binding.followButton.isVisible = true
                binding.requestedButton.isVisible = false
            }
            binding.followButton.setOnClickListener {
                onButtonClicked(item)
                binding.followingButton.isVisible = false
                binding.followButton.isVisible = false
                binding.requestedButton.isVisible = true
            }
            binding.clItemFollower.setOnClickListener {
                onItemClicked(item)
            }
            binding.requestedButton.setOnClickListener {
                onButtonClicked(item)
                binding.followingButton.isVisible = false
                binding.followButton.isVisible = true
                binding.requestedButton.isVisible = false
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
        val diffUtil = object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}
