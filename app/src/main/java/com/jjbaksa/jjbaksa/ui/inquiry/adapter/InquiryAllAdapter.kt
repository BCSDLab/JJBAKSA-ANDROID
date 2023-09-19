package com.jjbaksa.jjbaksa.ui.inquiry.adapter

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.inquiry.InquiryContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemInquiryBinding

class InquiryAllAdapter(
    private val context: Context
) : ListAdapter<InquiryContent, InquiryAllAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemInquiryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InquiryContent, position: Int) {
            item.title = item.title + " "
            val titleSpanText = SpannableString(item.title)
            binding.inquiryCreateTimeTextView.text = item.createdAt
            binding.inquiryNicknameTextView.text = item.createdBy
            if (item.isSecreted == 1) {
                binding.dropdownImageView.isVisible = false
                val lockImage = ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_lock_closed
                )
                lockImage?.let {
                    it.setBounds(5, 0, it.intrinsicWidth, it.intrinsicHeight)
                }
                titleSpanText.apply {
                    setSpan(
                        lockImage?.let { ImageSpan(it, DynamicDrawableSpan.ALIGN_BOTTOM) },
                        item.title.length - 1,
                        item.title.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                binding.inquiryTitleTextView.text = titleSpanText
            } else {
                binding.answerTextView.text = item.answer
                binding.dropdownImageView.isVisible = true
                binding.inquiryTitleTextView.text = item.title
                binding.dropdownImageView.setOnClickListener {
                    if (binding.answerTextView.isVisible) {
                        binding.answerTextView.visibility = View.GONE
                        binding.dropdownImageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_drop_down
                            )
                        )
                    } else {
                        binding.answerTextView.visibility = View.VISIBLE
                        binding.dropdownImageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_chevron_up
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInquiryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<InquiryContent>() {
            override fun areItemsTheSame(
                oldItem: InquiryContent,
                newItem: InquiryContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: InquiryContent,
                newItem: InquiryContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
