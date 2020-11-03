package com.rojer_ko.myschool.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.rojer_ko.myschool.R
import com.rojer_ko.myschool.utils.navigateOtherFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragmentContainer)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        main_bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_item_home -> {
                    navController?.navigateOtherFragment(R.id.homeScreenFragment)
                    true
                }

                R.id.menu_item_classes -> {
                    navController?.navigateOtherFragment(R.id.classesScreenFragment)
                    true
                }
                else -> false
            }
        }
    }
}