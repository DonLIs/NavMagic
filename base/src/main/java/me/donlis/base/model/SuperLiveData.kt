package me.donlis.base.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.donlis.base.interfaces.SuperCallback

/**
 * Created by CDL on 2021/10/29 17:09
 * 封装MutableLiveData 成功和失败处理
 */
open class SuperLiveData<T> : MutableLiveData<SuperResult<T>>(), SuperCallback<T> {

    /**
     * 成功
     */
    override fun onSuccess(result: T) {
        postValue(SuperResult.Success(result))
    }

    /**
     * 失败
     * @param throwable 错误信息
     */
    override fun onFailure(throwable: Throwable?) {
        postValue(SuperResult.Failure(throwable))
    }

    /**
     * 添加观察者
     * @param owner 生命周期
     * @param success 成功回调
     * @param failure 失败回调
     */
    fun observe(
        owner: LifecycleOwner,
        success: (value: T) -> Unit = {},
        failure: (e: Throwable?) -> Unit = {}
    ): SuperLiveData<T> {
        super.observe(owner, Observer<SuperResult<T>> {
            when (it) {
                is SuperResult.Failure -> {
                    failure(it.throwable)
                }
                is SuperResult.Success -> {
                    success(it.data)
                }
            }
        })
        return this
    }

}