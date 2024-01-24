package com.example.imageselector.gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageselector.model.Image
import com.example.imageselector.databinding.ItemLayoutBinding

class GalleryAdapter(
    val context: Context
) : ListAdapter<Image, GalleryAdapter.ViewHolder>(diffUtil){

    var onClickImageListener: OnClickImageListener? = null

    interface OnClickImageListener {
        fun onImageClick(image: Image, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        private val display = context.resources.displayMetrics
        fun bind(image: Image, position: Int) {
            binding.selectedImage = image

            binding.galleryView.also { galleryImage ->
                galleryImage.layoutParams =
                    LinearLayout.LayoutParams(display.widthPixels / 3 - 6, display.widthPixels / 3 - 6)
                Glide.with(context)
                    .load(image.uri)
                    .into(galleryImage)
            }

            binding.root.setOnClickListener {
                onClickImageListener?.onImageClick(image, position)
            }

        }
    }
    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }
}

