package com.jjbaksa.jjbaksa.ui.pin

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentPinMyReviewBinding
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinMyReviewAdapter
import com.jjbaksa.jjbaksa.ui.pin.viewmodel.PinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinMyReviewFragment : BaseFragment<FragmentPinMyReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_pin_my_review
    private val viewModel: PinViewModel by activityViewModels()
    private lateinit var myReviewAdapter: PinMyReviewAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun initView() {
        myReviewAdapter = PinMyReviewAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        binding.myReviewRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = myReviewAdapter
            itemAnimator = null
        }
        viewModel.myReviewUpdateCursor.value = MyReviewCursor.LATEST
        viewModel.pinReviewCursor.value = PinReviewCursor.MY_REVIEW
    }

    override fun initEvent() {
        binding.updateLayer.setOnClickListener {
            when (viewModel.myReviewUpdateCursor.value) {
                MyReviewCursor.LATEST -> {
                    viewModel.myReviewUpdateCursor.value = MyReviewCursor.STAR
                }

                MyReviewCursor.STAR -> {
                    viewModel.myReviewUpdateCursor.value = MyReviewCursor.LATEST
                }

                else -> {}
            }
        }
        binding.myReviewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemCount = linearLayoutManager.itemCount
                val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                if (lastPosition != - 1 && lastPosition >= (itemCount - 1) && viewModel.myReviewHasMore.value == true) {
                    viewModel.myReviewHasMore.value = false
                    when (viewModel.myReviewUpdateCursor.value) {
                        MyReviewCursor.LATEST -> {
                            viewModel.getMyReview(
                                placeId = viewModel.placeId.value.toString(),
                                idCursor = viewModel.myReview.value?.content?.get(lastPosition)?.id ?: return,
                                dateCursor = viewModel.myReview.value?.content?.get(lastPosition)?.createdAt ?: return,
                                size = 10
                            )
                        }
                        MyReviewCursor.STAR -> {
                            viewModel.getMyReview(
                                placeId = viewModel.placeId.value.toString(),
                                idCursor = viewModel.myReview.value?.content?.get(lastPosition)?.id ?: return,
                                rateCursor = viewModel.myReview.value?.content?.get(lastPosition)?.rate ?: return,
                                sort = "rate",
                                size = 10
                            )
                        }
                        else -> {}
                    }
                }
            }
        })
    }

    override fun subscribe() {
        viewModel.myReview.observe(viewLifecycleOwner) {
            if (it.content.isEmpty() && myReviewAdapter.currentList.isEmpty()) {
                binding.myReviewEmptyContainer.isVisible = true
            } else {
                binding.myReviewEmptyContainer.isVisible = false
                if (viewModel.writeNewMyReview.value == true) {
                    myReviewAdapter.submitList(it.content)
                    viewModel.setWriteNewMyReview(false)
                } else {
                    myReviewAdapter.submitList(myReviewAdapter.currentList + it.content)
                }
            }
        }
        viewModel.myReviewUpdateCursor.observe(viewLifecycleOwner) {
            when (it) {
                MyReviewCursor.LATEST -> {
                    myReviewAdapter.submitList(emptyList())
                    binding.updateReviewTextView.text = getString(R.string.newest)
                    viewModel.getMyReview(
                        placeId = viewModel.placeId.value.toString(),
                        size = 10
                    )
                }

                MyReviewCursor.STAR -> {
                    myReviewAdapter.submitList(emptyList())
                    binding.updateReviewTextView.text = getString(R.string.by_star_rating)
                    viewModel.getMyReview(
                        placeId = viewModel.placeId.value.toString(),
                        sort = "rate",
                        size = 10
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.pinReviewCursor.value = PinReviewCursor.MY_REVIEW
    }
}
