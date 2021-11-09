package me.donlis.base.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by CDL on 2021/10/26 10:49
 * BaseDataBindFragment主要处理ViewDataBinding逻辑
 */
open class BaseDataBindFragment<out T: ViewDataBinding> : BaseFragment() {

    //ViewDataBinding的句柄
    private var _binding: T? = null

    //提供binding的get方法
    protected open val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (getLayoutResId() != 0) {
            _binding = DataBindingUtil.inflate(layoutInflater, getLayoutResId(), container, false)
            //设置binding的lifecycleOwner
            //Navigation框架下使用 viewLifecycleOwner，而不是this(fragment)
            binding.lifecycleOwner = viewLifecycleOwner

            binding.root
        } else null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //mView销毁时，主动释放ViewDataBinding，防止内存泄漏
        _binding = null
    }

}