package com.jjbaksa.jjbaksa.ui.follower.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.follower.FollowContent
import com.jjbaksa.jjbaksa.databinding.ItemFollowBinding

class FollowRequestCheckAdapter(
    private val onAcceptClicked: (FollowContent) -> Unit,
    private val onDeleteClicked: (FollowContent) -> Unit,
) : ListAdapter<FollowContent, FollowRequestCheckAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowContent) {
            binding.followNameTextView.text = item.user?.nickname
            binding.followAccountTextView.text = "@" +item.user?.account

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
