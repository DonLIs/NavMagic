package me.donlis.base.ktx

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import me.donlis.base.R

/**
 * 导航到指定页面
 * resId： 资源id
 * args: 传递参数（可空）
 */
fun View.navigate(@IdRes resId: Int, args: Bundle?) {
    Navigation.findNavController(this).navigate(resId, args)
}

/**
 * 跨模块导航
 */
fun View.routerNav(uri: String) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(uri.toUri())
        .build()

    Navigation.findNavController(this).navigate(request, getNavOption())
}

/**
 * 跨模块导航
 * option： 导航配置项（动画）
 */
fun View.routerNav(uri: String, option: NavOptions? = getNavOption()) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(uri.toUri())
        .build()
    Navigation.findNavController(this).navigate(request, option)
}

/**
 * 跨模块导航
 * option： 导航配置项（动画）
 */
fun View.routerNav(request: NavDeepLinkRequest, option: NavOptions? = getNavOption()) {
    Navigation.findNavController(this).navigate(request, option)
}

/**
 * 默认的导航配置，设置了默认转场动画
 */
private fun getNavOption(): NavOptions {
    return navOptions {
        anim {
            enter = R.anim.slide_in_from_right
            exit = R.anim.slide_out_to_left
            popEnter = R.anim.slide_in_from_left
            popExit = R.anim.slide_out_to_right
        }
    }
}