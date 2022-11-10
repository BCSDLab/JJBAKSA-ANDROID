package com.jjbaksa.jjbaksa.ui.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding

class NaviHomeFragment : Fragment() {
    lateinit var binding : FragmentNaviHomeBinding
    private var isFloatingMenuButtonClicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_navi_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFloatingMenuInvisible()
        binding.buttonHamburger.setOnClickListener {
            if (isFloatingMenuButtonClicked) {
                setFloatingMenuInvisible()
            } else setFloatingMenuVisible()
        }
    }

    private fun setFloatingMenuInvisible() {
        with(binding) {
            buttonBookmark.visibility = GONE
            buttonFriend.visibility = GONE
            buttonMap.visibility = GONE

            textViewBookmark.visibility = GONE
            textViewFriend.visibility = GONE
            textViewFindStore.visibility = GONE

            imageButtonPlus.visibility = VISIBLE
            imageButtonMinus.visibility = VISIBLE
        }
        isFloatingMenuButtonClicked = false
    }

    private fun setFloatingMenuVisible() {
        with(binding) {
            buttonBookmark.visibility = VISIBLE
            buttonFriend.visibility = VISIBLE
            buttonMap.visibility = VISIBLE

            textViewBookmark.visibility = VISIBLE
            textViewFriend.visibility = VISIBLE
            textViewFindStore.visibility = VISIBLE

            imageButtonPlus.visibility = GONE
            imageButtonMinus.visibility = GONE
        }
        isFloatingMenuButtonClicked = true
    }

    companion object {
        fun newInstance(): NaviHomeFragment {
            val args = Bundle().apply {
            }
            val fragment = NaviHomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
