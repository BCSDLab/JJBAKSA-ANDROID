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
import com.jjbaksa.jjbaksa.databinding.ItemRecentlyActiveBinding

class RecentlyActiveAdapter() : ListAdapter<User, RecentlyActiveAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemRecentlyActiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.followNameTextView.text = item.nickname


            Glide.with(binding.root.context)
                .load(item.profileImage.url)
                .error(R.drawable.baseline_supervised_user_circle_24)
                .circleCrop()
                .into(binding.ivProfile)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecentlyActiveBinding.inflate(
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
