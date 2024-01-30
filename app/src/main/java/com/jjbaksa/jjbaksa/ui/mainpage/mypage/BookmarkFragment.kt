package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentBookmarkBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter.BookmarkAdapter
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_bookmark

    private val viewModel: MyPageViewModel by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val bookmarkAdapter: BookmarkAdapter by lazy {
        BookmarkAdapter(requireContext())
    }

    override fun initView() {
        viewModel.getScraps(null, null, 10)
        linearLayoutManager = LinearLayoutManager(context)
        binding.bookmarkRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = bookmarkAdapter
        }
    }

    override fun initEvent() {
        binding.bookmarkRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val itemCount = linearLayoutManager.itemCount
                val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.bookmarkHasMore.value == true) {
                    viewModel.bookmarkHasMore.value = false
                    viewModel.getScraps(
                        null,
                        viewModel.scrapsShops.value?.get(lastPosition)?.scrapId,
                        10
                    )
                    binding.progressContainer.isVisible = true
                }
            }
        })
        refreshBookmark()
    }

    private fun refreshBookmark() {
        binding.swipeRlContainer.setOnRefreshListener {
            viewModel.getScraps(null, null, 10)
        }
    }

    override fun subscribe() {
        viewModel.scrapsShops.observe(viewLifecycleOwner) {
            binding.progressContainer.isVisible = false
            binding.swipeRlContainer.isRefreshing = false
            if (it.isEmpty()) {
                binding.jjNoContentView.isVisible = true
            } else {
                binding.jjNoContentView.isVisible = false
                if (bookmarkAdapter.currentList.isEmpty()) {
                    bookmarkAdapter.submitList(it)
                } else {
                    if (bookmarkAdapter.currentList[0].equals(it[0])) {
                        bookmarkAdapter.submitList(it)
                    } else {
                        bookmarkAdapter.submitList(bookmarkAdapter.currentList + it)
                    }
                }
            }
        }
    }
}
