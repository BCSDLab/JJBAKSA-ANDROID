package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviMyPageBinding
import com.jjbaksa.jjbaksa.dialog.MyPageBottomSheetDialog
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import com.jjbaksa.jjbaksa.ui.setting.SettingActivity
import com.jjbaksa.jjbaksa.util.setExtendView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviMyPageFragment : BaseFragment<FragmentNaviMyPageBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_navi_my_page
    private val viewModel: MyPageViewModel by activityViewModels()

    private val reviewFragment by lazy { ReviewFragment() }
    private val bookmarkFragment by lazy { BookmarkFragment() }

    private val settingResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // Handle SettingActivity result
        }

    override fun initView() {
        requireActivity().setExtendView(binding.myPageConstraintLayout)
        initFragment(reviewFragment)
        setTabLayout()
        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.getUserProfile()
        observeData()
    }

    private fun observeData() {
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

    private fun initFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setTabLayout() {
        binding.myPageTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> initFragment(reviewFragment)
                    1 -> initFragment(bookmarkFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun initEvent() {
        onClickSettingImage()
        binding.profileImageView.setOnClickListener {
            MyPageBottomSheetDialog().show(parentFragmentManager, MY_PAGE_DIALOG_TAG)
        }
    }

    private fun onClickSettingImage() {
        binding.settingImageButton.setOnClickListener {
            settingResult.launch(Intent(requireContext(), SettingActivity::class.java))
        }
    }

    override fun subscribe() {
    }

    companion object {
        const val MY_PAGE_DIALOG_TAG = "MY_PAGE_DIALOG_TAG"
    }
}
