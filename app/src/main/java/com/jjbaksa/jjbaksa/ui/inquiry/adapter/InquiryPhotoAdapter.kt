package com.jjbaksa.jjbaksa.ui.inquiry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.jjbaksa.jjbaksa.databinding.ItemInquiryPhotoBinding

class InquiryPhotoAdapter(
    private val onClickClose: (Int) -> Unit
) : ListAdapter<String, InquiryPhotoAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemInquiryPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String, position: Int) {
            binding.inquiryImageView.load(image) {
                transformations(
                    RoundedCornersTransformation(
                        topLeft = 10f,
                        topRight = 10f,
                        bottomLeft = 10f,
                        bottomRight = 10f
                    )
                )
            }
            binding.deleteButton.setOnClickListener {
                onClickClose(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInquiryPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
