package com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.jjbaksa.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel()
