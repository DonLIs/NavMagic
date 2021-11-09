package me.donlis.base.interfaces

/**
 * Created by CDL on 2021/10/29 15:04
 * 组件接口
 */
interface IComponent {

    fun onBeforeInitialize()

    fun onInitView()

    fun onAfterInitialize(isFirstLoad: Boolean)

}