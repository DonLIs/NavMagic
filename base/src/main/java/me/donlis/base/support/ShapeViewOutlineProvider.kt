package me.donlis.base.support

import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import kotlin.math.min

/**
 *
 */
class ShapeViewOutlineProvider {

    /**
     * Desc:圆角
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class Round(var corner: Float) : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                    0,
                    0,
                    view.width,
                    view.height,
                    corner
            )
        }
    }

    /**
     * Desc:圆形
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class Circle : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val min = min(view.width, view.height)
            val left = (view.width - min) / 2
            val top = (view.height - min) / 2
            outline.setOval(left, top, min, min)
        }
    }
}