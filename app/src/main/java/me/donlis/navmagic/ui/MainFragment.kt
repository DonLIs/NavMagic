package me.donlis.navmagic.ui

import android.os.Bundle
import androidx.navigation.Navigation
import me.donlis.base.core.BaseDataBindFragment
import me.donlis.base.core.BaseFragment
import me.donlis.navmagic.R
import me.donlis.navmagic.databinding.FragmentMainBinding


/**
 *
 */
class MainFragment : BaseDataBindFragment<FragmentMainBinding>() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun initStatusBarColor(): Int {
        return R.color.base_transparent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onInitView() {
        binding.btn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_main_to_blank)
        }
    }

    override fun onObserver() {

    }

    override fun onAfterInitialize(isFirstLoad: Boolean) {

    }

}