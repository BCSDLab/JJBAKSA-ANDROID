package com.jjbaksa.jjbaksa.ui.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R

class NaviWriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_navi_write, container, false)
    }

    companion object {
        fun newInstance(): NaviWriteFragment {
            val args = Bundle().apply {
            }
            val fragment = NaviWriteFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
