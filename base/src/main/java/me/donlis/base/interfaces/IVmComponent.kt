package me.donlis.base.interfaces

import me.donlis.base.model.ComponentAction

/**
 * Created by CDL on 2021/10/29 16:06
 * ViewModel组件接口
 */
interface IVmComponent {

    fun onObserver()

    fun onAction(action: ComponentAction)

}