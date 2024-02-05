package com.jjbaksa.jjbaksa.ui.follower.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemFollowBinding

class FollowerAdapter(
    private val onFollowButtonClicked: (User) -> Unit,
    private val onFollowingButtonClicked: (User) -> Unit,
    private val onItemClicked: (User) -> Unit
) : ListAdapter<User, FollowerAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.followNameTextView.text = item.nickname
            binding.followAccountTextView.text = "@" + item.account

            Glide.with(binding.root.context)
                .load(item.profileImage.url)
                .error(R.drawable.baseline_supervised_user_circle_24)
                .circleCrop()
                .into(binding.ivProfile)

            binding.followingButton.isVisible = item.followedType == FOLLOWED
            binding.followButton.isVisible = item.followedType == NONE
            binding.requestedButton.isVisible = item.followedType == REQUEST_SENT

            binding.followButton.setOnClickListener {
                onFollowButtonClicked(item)
                submitList(currentList.filter { it != item })
            }

            binding.followingButton.setOnClickListener {
                onFollowingButtonClicked(item)
                submitList(currentList.filter { it != item })
            }

            binding.clItemFollower.setOnClickListener {
                onItemClicked(item)
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

        const val FOLLOWED = "FOLLOWED"
        const val NONE = "NONE"
        const val REQUEST_SENT = "REQUEST_SENT"
    }
}
