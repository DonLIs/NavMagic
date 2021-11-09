package me.donlis.navmagic.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import me.donlis.base.core.BaseFragment
import me.donlis.navmagic.R

/**
 *
 */
class BlankFragment : BaseFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_blank
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onInitView() {
        val btn: Button? = view?.findViewById(R.id.btn)
        btn?.setOnClickListener {
            val navController = Navigation.findNavController(it)

            val request = NavDeepLinkRequest.Builder
                .fromUri("nav://me.donlis.navmagic/detail?id=123".toUri())
                .build()

            val option = navOptions {
                anim {
                    enter = R.anim.slide_in_from_right
                    exit = R.anim.slide_out_to_left
                    popEnter = R.anim.slide_in_from_left
                    popExit = R.anim.slide_out_to_right
                }
            }
            navController.navigate(request, option)
        }
    }

}