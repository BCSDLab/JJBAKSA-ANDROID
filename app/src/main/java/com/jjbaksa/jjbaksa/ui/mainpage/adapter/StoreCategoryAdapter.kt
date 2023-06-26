package com.jjbaksa.jjbaksa.ui.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjbaksa.jjbaksa.databinding.ItemStoreCategoryBinding

class StoreCategoryAdapter(
    private val categoryList: List<StoreCategoryItem>
) : RecyclerView.Adapter<StoreCategoryAdapter.StoreCategoryViewHolder>() {

    inner class StoreCategoryViewHolder(private val binding: ItemStoreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoreCategoryItem) {
            Glide.with(binding.root)
                .load(item.icon)
                .into(binding.imageViewCategoryIcon)
            binding.textViewCategoryContent.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreCategoryViewHolder =
        StoreCategoryViewHolder(ItemStoreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StoreCategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size
}

data class StoreCategoryItem(
    val icon: Int,
    val content: String
)
