package com.jjbaksa.jjbaksa.ui.pin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.jjbaksa.databinding.ItemPhotoBinding

class PinReviewImageAdapter(
    private val onDelete: (Int) -> Unit
) : ListAdapter<String, PinReviewImageAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: String, position: Int) {
            Glide.with(binding.photoImageView)
                .load(photo)
                .into(binding.photoImageView)

            binding.deleteButton.setOnClickListener {
                onDelete(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoList = currentList[position]
        holder.bind(photoList, position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}