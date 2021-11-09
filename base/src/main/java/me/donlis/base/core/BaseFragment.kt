package me.donlis.base.core

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import me.donlis.base.R
import me.donlis.base.interfaces.IVmComponent
import me.donlis.base.model.ComponentAction
import me.donlis.base.interfaces.ILoading
import org.jetbrains.annotations.Nullable

/**
 * Created by CDL on 2021/10/26 10:35
 * BaseFragment 主要处理UI相关逻辑，DataBinding交给BaseDataBindFragment
 */
open class BaseFragment : SuperFragment(), IVmComponent, ILoading, SimpleImmersionOwner {

    /**
     * ImmersionBar代理类
     */
    private val mSimpleImmersionProxy by lazy {
        SimpleImmersionProxy(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    /**
     * 设置状态栏字体颜色
     * true: 黑色， false: 白色
     */
    protected open fun isStatusBarDark(): Boolean {
        return true
    }

    /**
     * 设置布局资源id
     */
    protected open fun getLayoutResId(): Int {
        return 0
    }

    /**
     * 设置状态栏背景颜色
     */
    protected open fun initStatusBarColor(): Int {
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        //代理回调
        mSimpleImmersionProxy.onDestroy()
    }

    override fun initImmersionBar() {
        //初始化状态栏字体和背景颜色
        ImmersionBar.with(this).apply {
            // 状态栏字体
            statusBarDarkFont(isStatusBarDark())
            // 状态栏背景颜色
            if (initStatusBarColor() != 0) {
                statusBarColor(initStatusBarColor())
            } else {
                statusBarColor(R.color.base_white)
            }
            //全屏
            fullScreen(true)
        }.also {
            it.init()
        }
    }

    /**
     * 设置状态栏可用
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }

    /**
     * 创建fragment的ViewModel
     * 建议：在by lazy方法内创建
     */
    protected open fun <VM : SuperViewModel> fragmentViewModel(clazz: Class<VM>): VM {
        return ViewModelProvider(this).get(clazz).apply {
            this.actionLiveData.observe(viewLifecycleOwner) {
                onAction(it)
            }
        }
    }

    /**
     * 创建activity的ViewModel
     * 建议：在by lazy方法内创建
     */
    protected open fun <VM : SuperViewModel> activityViewModel(clazz: Class<VM>): VM {
        return ViewModelProvider(requireActivity()).get(clazz).apply {
            this.actionLiveData.observe(viewLifecycleOwner) {
                onAction(it)
            }
        }
    }

    override fun onAfterInitialize(isFirstLoad: Boolean) {
        onObserver()
        super.onAfterInitialize(isFirstLoad)
    }

    /**
     * 在此处监听订阅
     */
    override fun onObserver() {
        Log.d(javaClass.simpleName, "$TAG:onObserver")
    }

    /**
     * 处理动作回调
     */
    override fun onAction(action: ComponentAction) {
        when (action) {
            is ComponentAction.ShowLoading -> {
                showLoading()
            }
            is ComponentAction.DismissLoading -> {
                dismissLoading()
            }
        }
    }

    /**
     * 展示Loading
     */
    override fun showLoading() {

    }

    /**
     * 隐藏Loading
     */
    override fun dismissLoading() {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //代理回调
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //代理回调
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //代理回调
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //代理回调
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

}