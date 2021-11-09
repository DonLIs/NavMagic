package me.donlis.base.glide

import android.util.ArrayMap
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.manager.LifecycleListener
import com.bumptech.glide.manager.RequestManagerTreeNode
import java.util.*
import com.bumptech.glide.manager.Lifecycle as GlideLifecycle

/**
 * Created by CDL on 2021/10/19 14:09
 * Glide 自己管理view的生命周期
 */
class KGlide {

    companion object {
        //集合，存储下载图片的请求
        private val lifecycleMap = ArrayMap<LifecycleOwner, RequestManager>()

        /**
         * 与Glide#with使用一样
         * 增加自己管理view的生命周期
         */
        @MainThread
        fun with(fragment: Fragment) : RequestManager {
            val viewLifecycleOwner = fragment.viewLifecycleOwner

            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                throw IllegalStateException("View is already destroyed.")
            }

            if (lifecycleMap[viewLifecycleOwner] == null) {
                val appContext = fragment.requireContext().applicationContext
                lifecycleMap[viewLifecycleOwner] = RequestManager(
                    Glide.get(appContext),
                    KLifecycle(viewLifecycleOwner.lifecycle),
                    KEmptyRequestManagerTreeNode(),
                    appContext
                )
            }
            return lifecycleMap[viewLifecycleOwner]!!
        }
    }

    class KEmptyRequestManagerTreeNode : RequestManagerTreeNode {
        override fun getDescendants(): MutableSet<RequestManager> {
            return HashSet<RequestManager>()
        }
    }

    /**
     * 继承Glide的生命周期回调
     * 根据传入view的Lifecycle状态处理Glide的请求回调
     */
    class KLifecycle(private val lifecycle: Lifecycle) : GlideLifecycle {

        private val lifecycleListeners = Collections.newSetFromMap(WeakHashMap<LifecycleListener, Boolean>())

        private val lifecycleObserver = object : DefaultLifecycleObserver {

            override fun onStart(owner: LifecycleOwner) {
                lifecycleListeners.forEach {
                    it.onStart()
                }
            }

            override fun onStop(owner: LifecycleOwner) {
                lifecycleListeners.forEach {
                    it.onStop()
                }
            }

            override fun onDestroy(owner: LifecycleOwner) {
                lifecycleListeners.forEach {
                    it.onDestroy()
                }

                lifecycleMap.remove(owner)
                lifecycleListeners.clear()
                lifecycle.removeObserver(this)
            }

        }

        init {
            lifecycle.addObserver(lifecycleObserver)
        }

        override fun addListener(listener: LifecycleListener) {
            lifecycleListeners.add(listener)
            when (lifecycle.currentState) {
                Lifecycle.State.STARTED, Lifecycle.State.RESUMED -> listener.onStart()
                Lifecycle.State.DESTROYED -> listener.onDestroy()
                else -> listener.onStop()
            }
        }

        override fun removeListener(listener: LifecycleListener) {
            lifecycleListeners.remove(listener)
        }

    }

}