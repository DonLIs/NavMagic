package me.donlis.base.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import me.donlis.base.interfaces.ILoading

/**
 * Created by CDL on 2021/10/27 17:31
 * Activity基类
 */
open class SuperActivity : AppCompatActivity(), ILoading {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化状态栏
        initActionBar()
    }

    /**
     * 初始化状态栏
     */
    protected open fun initActionBar() {
        ImmersionBar.with(this).apply {
            // 状态栏字体
            statusBarDarkFont(isStatusBarDark())
            // 状态栏背景颜色
            if (initStatusBarColor() != 0) {
                statusBarColor(initStatusBarColor())
            }
            //全屏
            fullScreen(true)
            init()
        }
    }

    /**
     * 状态栏背景颜色
     * return ResId
     */
    protected open fun initStatusBarColor(): Int {
        return 0
    }

    /**
     * 状态栏字体
     * return true:深色 false:浅色
     */
    protected open fun isStatusBarDark(): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

}