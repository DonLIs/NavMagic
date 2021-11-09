package me.donlis.base.ktx

import me.donlis.base.utils.UiUtils
import kotlin.math.roundToInt

/**
 * 转换为PX值
 */
val Float.dp: Int get() = this.toPX()
val Int.dp: Int get() =  this.toPX()

/**
 * 转换为DP值
 */
val Float.px: Int get() = this.toDP().roundToInt()
val Int.px: Int get() = this.toDP().roundToInt()


fun Long.toDP(): Float {
    return UiUtils.px2dip(this.toFloat())
}


fun Float.toDP(): Float {
    return UiUtils.px2dip(this)
}


fun Int.toDP(): Float {
    return UiUtils.px2dip(this.toFloat())
}


fun Long.toPX(): Int {
    return UiUtils.dip2px(this.toFloat())
}


fun Float.toPX(): Int {
    return UiUtils.dip2px(this)
}


fun Int.toPX(): Int {
    return UiUtils.dip2px(this.toFloat())
}