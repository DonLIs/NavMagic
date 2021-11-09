package me.donlis.base.ktx

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import me.donlis.base.support.ShapeViewOutlineProvider


/**
 * 展示
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * 不可见
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * 隐藏
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * 展示or隐藏
 */
inline fun View.visibleOrGone(show: View.() -> Boolean = { true }) {
    visibility = if (show(this)) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 展示or不可见
 */
inline fun View.visibleOrInvisible(show: View.() -> Boolean = { true }) {
    visibility = if (show(this)) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

/**
 * 点击事件，防止重复点击
 */
inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 设置边距
 */
fun View?.setMargin(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        start?.let {
            this.marginStart = start
        }
        top?.let {
            this.topMargin = top
        }
        end?.let {
            this.marginEnd = end
        }
        bottom?.let {
            this.bottomMargin = bottom
        }
    }
}

/**
 * 设置View圆角矩形
 */
fun <T : View> T.roundCorner(corner: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (outlineProvider == null || outlineProvider !is ShapeViewOutlineProvider.Round) {
            outlineProvider = ShapeViewOutlineProvider.Round(corner.toFloat())
        } else if (outlineProvider != null && outlineProvider is ShapeViewOutlineProvider.Round) {
            (outlineProvider as ShapeViewOutlineProvider.Round).corner = corner.toFloat()
        }
        clipToOutline = true
    }
}

/**
 * 设置View为圆形
 */
fun <T : View> T.circle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (outlineProvider == null || outlineProvider !is ShapeViewOutlineProvider.Circle) {
            outlineProvider = ShapeViewOutlineProvider.Circle()
        }
        clipToOutline = true
    }
}