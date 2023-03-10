package com.jjbaksa.jjbaksa.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.domain.model.Image
import com.jjbaksa.jjbaksa.databinding.ItemLayoutBinding

class GalleryAdapter() : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    lateinit var imageList: ArrayList<Image>
    lateinit var uriArr: ArrayList<String>
    lateinit var context: Context
    var itemClick: ItemClick? = null

    constructor(
        context: Context,
        imageList: ArrayList<Image>,
        uriArr: ArrayList<String>,
    ) : this() {
        this.imageList = imageList
        this.uriArr = uriArr
        this.context = context
    }

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


        holder.galleryView.setOnClickListener {
            itemClick?.onClick(it, position)
        }


        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    inline fun setOnClickListener(crossinline galleryView: (Int) -> Unit) {
        this.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                galleryView(position)
            }
        }
    }


}
