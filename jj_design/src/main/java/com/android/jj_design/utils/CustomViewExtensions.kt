package com.android.jj_design.utils

import android.content.res.Resources
import com.android.jj_design.button.Add
import com.android.jj_design.button.JjMapType
import com.android.jj_design.button.Minus
import com.android.jj_design.button.Position
import com.android.jj_design.utils.button.Confirm
import com.android.jj_design.utils.button.Delete
import com.android.jj_design.utils.button.Follow
import com.android.jj_design.utils.button.Following
import com.android.jj_design.utils.button.JjChipType
import com.android.jj_design.utils.button.Requested

val Int.px get(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toJjChipType get(): JjChipType? = when (this) {
    1 -> Delete
    2 -> Follow
    3 -> Following
    4 -> Confirm
    5 -> Requested
    else -> null
}
