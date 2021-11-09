package me.donlis.base.interfaces

/**
 * Created by CDL on 2021/10/29 14:28
 * 成功、失败回调
 */
interface SuperCallback<T> {

    /**
     * @param result 成功返回的实体
     */
    fun onSuccess(result: T)

    /**
     * @param throwable 错误信息对象
     */
    fun onFailure(throwable: Throwable?)

}