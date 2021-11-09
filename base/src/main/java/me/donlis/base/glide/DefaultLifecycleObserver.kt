package me.donlis.base.glide

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by CDL on 2021/10/19 14:39
 * KGlide的生命周期监听
 */
interface DefaultLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onStart(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner)

}