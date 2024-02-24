package com.android.jj_design.utils.button

import com.android.jj_design.utils.DesignConstants.CONFIRM
import com.android.jj_design.utils.DesignConstants.DELETE
import com.android.jj_design.utils.DesignConstants.FOLLOW
import com.android.jj_design.utils.DesignConstants.FOLLOWING
import com.android.jj_design.utils.DesignConstants.REQUESTED

sealed class JjChipType(val name: String?)
object Delete : JjChipType(DELETE)
object Follow : JjChipType(FOLLOW)
object Following : JjChipType(FOLLOWING)
object Confirm : JjChipType(CONFIRM)
object Requested : JjChipType(REQUESTED)

