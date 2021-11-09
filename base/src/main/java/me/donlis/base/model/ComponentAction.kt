package me.donlis.base.model

import android.app.Activity
import android.app.Dialog

/**
 * 与组件的通信命令
 */
open class ComponentAction {

    /**
     * 展示加载弹窗
     */
    class ShowLoading(val message: CharSequence? = null) : ComponentAction()

    /**
     * 关闭加载弹窗
     */
    class DismissLoading() : ComponentAction()

    /**
     * 展示弹窗
     */
    class ShowDialog(val build: (activity: Activity?) -> Dialog?) : ComponentAction()

    /**
     * toast
     */
    class Toast(val message: CharSequence?) : ComponentAction()

    /**
     * 跳转页面
     * @param path 路径
     * @param tag 自定义数据
     */
    class Jump(val path: String, val tag: Any?) : ComponentAction()
}