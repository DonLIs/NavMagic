package me.donlis.base.core

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import me.donlis.base.interfaces.IComponent

/**
 *  * 1. Databinding 需要 onDestroyView 设置为 Null
 * 一般会在 Fragment 的 onCreateView 模板函数中初始化 ViewDataBing，
 * 这样就会有 Fragment 持有对 View 的引用。但是 fragment 和 view 的生命周期是不一样的，
 * 当 view 被销毁的时候，fragment 并不一定被销毁，所以一定要在 fragment.onDestroyView 函数
 * 中把对 view 的引用变量设置为 null，不然会导致 view 回收不掉
 *
 * 2. 当 Databinding 遇到错的 lifecycle.
 * Databinding 确实很强大，能把数据和 UI 进行绑定，这里对 UI 就有个要求，
 * UI 一定要知道自己的生命周期的，知道自己什么时候处于 Active 和 InActive 的状态。
 * 所以我们必须要给 databinding 设置一个正确的生命周期
 *
 * 3. Glide 自我管理的生命周期
 *  KGlide
 *
 * 4. Android 组件的生命周期自我管理
 * 在 Fragment.onViewCreated()的回调时，fragment的生命周期还处于INITIALIZED
 * LiveData能够反注册的前提是fragment的生命周期处于CREATED
 * 所以在Fragment.onViewStateRestored()回调时，fragment的生命周期才处于CREATED
 *
 * 5.当 ViewPager2 遇到 Navigation
 *  FragmentStateAdapter使用两个参数的构造函数，第一个传fragment自身，第二个传fragment的viewLifecycleOwner
 *
 * 6. ViewPager2 设置 Adapter 导致的 Fragment 重建问题
 *  设置ViewPager2的adapter时，如果是重建，则会销毁之前的fragment
 *  正确的代码
 *  if (viewPager2.adapter == null) {
 *      val adapter = ...
 *      adapter.data = data
 *      viewPager2.adapter = adapter
 *  } else {
 *      viewPager2.adapter.data = data
 *  }
 *
 *  7. 在 Navigation 的框架下，手动进行 Fragment 管理需要注意什么？
 *  尽量不要手动通过 FragmentManager 来进行操作，而是使用Navigation 的框架来导航
 *
 *
 *  SuperFragment主要处理生命周期回调等
 *  有关UI的逻辑交给BaseFragment
 */
open class SuperFragment : Fragment(), IComponent {

    var TAG = "SuperFragment"

    //第一次加载页面
    private var isFirstLoadFragment = false

    //第一次可见
    private var isViewFirstVisit = false

    //当前可见状态
    private var currentVisitStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //记录第一次进入页面
        isFirstLoadFragment = true
    }

    override fun onStart() {
        super.onStart()
        Log.d(javaClass.simpleName, "$TAG:onStart")
    }

    override fun onResume() {
        Log.d(javaClass.simpleName, "$TAG:onResume")
        super.onResume()
        //分发可见状态
        dispatchVisit(true)
    }

    override fun onPause() {
        Log.d(javaClass.simpleName, "$TAG:onPause")
        super.onPause()
        //分发不可见状态
        dispatchVisit(false)
    }

    override fun onStop() {
        super.onStop()
        Log.d(javaClass.simpleName, "$TAG:onStop")
    }

    override fun onDestroyView() {
        Log.d(javaClass.simpleName, "$TAG:onDestroyView")
        super.onDestroyView()
        //此处mView销毁，处理当前变量状态
        isFirstLoadFragment = false
        isViewFirstVisit = false
        currentVisitStatus = false
    }

    override fun onDestroy() {
        Log.d(javaClass.simpleName, "$TAG:onDestroy")
        super.onDestroy()
        //此处fragment销毁，处理当前变量状态
        isFirstLoadFragment = false
        isViewFirstVisit = false
        currentVisitStatus = false
    }

    /**
     * 处理可见状态改变后的回调
     */
    private fun dispatchVisit(visit: Boolean) {
        this.currentVisitStatus = visit

        if (visit) {
            //可见
            if (isViewFirstVisit) {
                onViewResume()
            } else {
                //标记第一次加载页面
                isViewFirstVisit = true
                onViewFirstVisible()
            }
        } else {
            //不可见
            if (isViewFirstVisit) {
                onViewPause()
            }
        }
    }

    /**
     * 在初始化view前的回调
     */
    override fun onBeforeInitialize() {
        Log.d(javaClass.simpleName, "$TAG:onBeforeInitialize")
    }

    /**
     * 在此处初始化view
     */
    override fun onInitView() {
        Log.d(javaClass.simpleName, "$TAG:onInitView")
    }

    /**
     * 在初始化view和监听订阅后的回调
     * isFirstLoad： 是否第一次加载页面
     * 第一次初始化view后返回：ture，返回栈重新初始化view时返回：false
     */
    override fun onAfterInitialize(isFirstLoad: Boolean) {
        Log.d(javaClass.simpleName, "$TAG:isFirstLoad:$isFirstLoad")
    }

    /**
     * 第一次加载页面，或返回栈时（仅在Navigation框架下回调）
     * 步骤：
     * 1、初始化前
     * 2、初始化
     * 3、监听订阅(在子类实现)
     * 4、初始化后
     */
    protected open fun onViewFirstVisible() {
        onBeforeInitialize()
        onInitView()
        onAfterInitialize(isFirstLoadFragment)
    }

    /**
     * 可见回调
     */
    protected open fun onViewResume() {
        Log.d(javaClass.simpleName, "$TAG:onViewResume")
    }

    /**
     * 不可见回调
     */
    protected open fun onViewPause() {
        Log.d(javaClass.simpleName, "$TAG:onViewPause")
    }

}