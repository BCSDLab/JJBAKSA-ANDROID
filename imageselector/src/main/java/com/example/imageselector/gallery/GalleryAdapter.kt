package com.example.imageselector.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageselector.model.Image
import com.example.imageselector.databinding.ItemLayoutBinding

class GalleryAdapter(
    val context: Context,
    private val imageList: ArrayList<Image>,
    private val uriArr: ArrayList<String>,
    private val maxNum: Int,
    val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private val selectedImages = ArrayList<Int>()

    class ViewHolder(
        private val binding: ItemLayoutBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val galleryView = binding.galleryView
        val item = binding.item

        fun bind(image: Image) {
            binding.selectedImage = image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val display = context.resources.displayMetrics
        holder.galleryView.layoutParams =
            LinearLayout.LayoutParams(display.widthPixels / 3 - 6, display.widthPixels / 3 - 6)

        Glide.with(context)
            .load(uriArr[position])
            .into(holder.galleryView)

        holder.itemView.setOnClickListener {
            if (selectedImages.size < maxNum) {
                if (!selectedImages.contains(position)) {
                    selectedImages.add(position)
                    onClick(position)
                } else {
                    selectedImages.remove(position)
                    onClick(position)
                }
            } else {
                if (selectedImages.contains(position)) {
                    selectedImages.remove(position)
                    onClick(position)
                }
            }
        }

        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
