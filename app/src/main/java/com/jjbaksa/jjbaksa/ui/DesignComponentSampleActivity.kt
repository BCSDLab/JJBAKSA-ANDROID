package com.jjbaksa.jjbaksa.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.jj_design.utils.button.Add
import com.android.jj_design.utils.button.Confirm
import com.android.jj_design.utils.button.Delete
import com.android.jj_design.utils.button.Follow
import com.android.jj_design.utils.button.Following
import com.android.jj_design.utils.button.Minus
import com.android.jj_design.utils.button.Position
import com.android.jj_design.utils.button.Requested
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ActivityDesignComponentSampleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DesignComponentSampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDesignComponentSampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDesignComponentSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initJjButton()
        initJjChipButton()
        initJjTrendButton()
        initJjMapButton()
        initJjPlusButton()
    }

    private fun initJjButton() {
        CoroutineScope(Dispatchers.Main).launch {
            while (this.isActive) {
                binding.jjButton.setErrorBackground()
                binding.jjButton.text = "오류 버튼"
                delay(2000)
                binding.jjButton.isEnabled = true
                binding.jjButton.text = "활성화 버튼"
                delay(2000)
                binding.jjButton.isEnabled = false
                binding.jjButton.text = "비활성화 버튼"
                delay(2000)
            }
        }
    }

    private fun initJjChipButton() {
        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                binding.jjChipButton.chipItemType = Delete
                delay(2000)
                binding.jjChipButton.chipItemType = Confirm
                delay(2000)
                binding.jjChipButton.chipItemType = Requested
                delay(2000)
                binding.jjChipButton.chipItemType = Follow
                delay(2000)
                binding.jjChipButton.chipItemType = Following
                delay(2000)
            }
        }
    }

    private fun initJjTrendButton() {
        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                binding.jjTrendButton.isEnabled = true
                binding.jjTrendButton.text = "#성수동 맛집"
                delay(2000)
                binding.jjTrendButton.isEnabled = false
                binding.jjTrendButton.text = "#카페 마이야르"
                delay(2000)
            }
        }
    }

    private fun initJjMapButton() {
        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                binding.jjMapButton.setJjMapType(Add)
                delay(2000)
                binding.jjMapButton.setJjMapType(Minus)
                delay(2000)
                binding.jjMapButton.setJjMapType(Position)
                delay(2000)
            }
        }
    }

    private fun initJjPlusButton() {
        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                binding.jjPlusButton.iconDrawable = com.android.jj_design.R.drawable.icon_add
                delay(2000)
                binding.jjPlusButton.iconDrawable = com.android.jj_design.R.drawable.icon_minus
                delay(2000)
                binding.jjPlusButton.iconDrawable = com.android.jj_design.R.drawable.icon_pencil
                delay(2000)
            }
        }
    }
}
