package me.donlis.base.core

import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by CDL on 2021/10/27 9:58
 *
 */
open class SuperApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

}