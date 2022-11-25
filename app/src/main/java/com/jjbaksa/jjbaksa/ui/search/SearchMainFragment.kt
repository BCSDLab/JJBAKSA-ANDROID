package com.jjbaksa.jjbaksa.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentSearchMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMainFragment : Fragment() {

    private lateinit var binding: FragmentSearchMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_main, container, false)

        return binding.root
    }
}
