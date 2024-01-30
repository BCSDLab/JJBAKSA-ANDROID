package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviMyPageBinding
import com.jjbaksa.jjbaksa.dialog.MyPageBottomSheetDialog
import com.jjbaksa.jjbaksa.ui.follower.FollowerActivity
import com.jjbaksa.jjbaksa.ui.mainpage.MainPageActivity
import com.jjbaksa.jjbaksa.ui.mainpage.home.NaviHomeFragment
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter.MyPageAdapter
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import com.jjbaksa.jjbaksa.ui.setting.SettingActivity
import com.jjbaksa.jjbaksa.util.setExtendView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviMyPageFragment : BaseFragment<FragmentNaviMyPageBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_navi_my_page
    private val viewModel: MyPageViewModel by activityViewModels()

    private var backClickTime = 0L

    private val settingResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // Handle SettingActivity result
        }
    override var onBackPressedCallBack: OnBackPressedCallback? =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as MainPageActivity).showHomeFragment()

                if (parentFragmentManager.findFragmentByTag(NaviHomeFragment.TAG)?.isVisible == true) {
                    if (System.currentTimeMillis() - backClickTime >= 2000L) {
                        backClickTime = System.currentTimeMillis()
                        showSnackBar(getString(R.string.back_finish))
                    } else {
                        requireActivity().finish()
                    }
                }
            }
        }

    override fun initView() {
        requireActivity().setExtendView(binding.myPageConstraintLayout)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.getUserProfile()
        initTabLayoutAndViewPager()
    }

    private fun initTabLayoutAndViewPager() {
        binding.myPageViewPager.adapter = MyPageAdapter(requireActivity())

        TabLayoutMediator(
            binding.myPageTabLayout,
            binding.myPageViewPager
        ) { tab, position ->
            binding.myPageViewPager.currentItem = tab.position
            tab.text = resources.getStringArray(R.array.my_page_tab)[position]
        }.attach()
    }

    override fun initEvent() {
        onClickSettingImage()
        binding.profileImageView.setOnClickListener {
            MyPageBottomSheetDialog().show(parentFragmentManager, MY_PAGE_DIALOG_TAG)
        }

        binding.followerTextView.setOnClickListener {
            settingResult.launch(Intent(requireContext(), FollowerActivity::class.java))
        }
    }

    private fun onClickSettingImage() {
        binding.settingImageButton.setOnClickListener {
            settingResult.launch(Intent(requireContext(), SettingActivity::class.java))
        }
    }

    override fun subscribe() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.profileNameTextView.text = it
        }
        viewModel.profileImage.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.profileImageView.load(R.drawable.baseline_supervised_user_circle_24) {
                    transformations(CircleCropTransformation())
                }
            } else {
                binding.profileImageView.load(it) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    companion object {
        const val MY_PAGE_DIALOG_TAG = "MY_PAGE_DIALOG_TAG"
        fun newInstance() = NaviMyPageFragment()
        val TAG = "NaviMyPageFragment"
    }
}
