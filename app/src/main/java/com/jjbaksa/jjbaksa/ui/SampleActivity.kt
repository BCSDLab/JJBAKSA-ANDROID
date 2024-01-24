package com.jjbaksa.jjbaksa.ui

import android.text.InputType
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySampleBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog

class SampleActivity : BaseActivity<ActivitySampleBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_sample

    override fun initView() {
        binding.jetTailimg.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            confirmDialogButton.setOnClickListener {
                ConfirmDialog("타이틀", "메시지 입니다", "확인", { it.dismiss() }).show(supportFragmentManager, ConfirmDialog.TAG)
            }
            jetTailtext.setTailButtonOnClickListener {
                jetTailtext.setTailButtonText(getString(R.string.id_check), R.drawable.shape_rect_ff7f23_solid_ffffff_stroke_radius_100, R.color.color_ff7f23)
            }
            jetTailimg.setTailImgOnClickListener {
                jetTailimg.setTailImgSelected(!jetTailimg.getTailImgSelected())
                if (jetTailimg.getTailImgSelected()) {
                    jetTailimg.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                } else {
                    jetTailimg.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                }
            }
        }
    }
}
