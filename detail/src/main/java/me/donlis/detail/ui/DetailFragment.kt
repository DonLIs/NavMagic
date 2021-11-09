package me.donlis.detail.ui

import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import me.donlis.base.core.BaseDataBindFragment
import me.donlis.detail.R
import me.donlis.detail.databinding.FragmentDetailBinding


/**
 *
 */
class DetailFragment : BaseDataBindFragment<FragmentDetailBinding>() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_detail
    }

    override fun onBeforeInitialize() {
        arguments?.get("id")?.let {
            Log.d("DetailFragment", it.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onInitView() {
        binding.btn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    override fun onObserver() {

    }

    override fun onAfterInitialize(isFirstLoad: Boolean) {

    }

}