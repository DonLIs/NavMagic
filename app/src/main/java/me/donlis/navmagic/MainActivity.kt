package me.donlis.navmagic

import android.os.Bundle
import me.donlis.base.core.SuperActivity

class MainActivity : SuperActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}