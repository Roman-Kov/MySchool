package com.rojer_ko.myschool.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

fun AppCompatActivity.showToast(message:String){
    Toast.makeText(this, message,Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message:String){
    Toast.makeText(this.context,message,Toast.LENGTH_LONG).show()
}

fun NavController.navigateOtherFragment(fragmentId: Int){
    if(fragmentId != this.currentDestination?.id){
        this.navigate(fragmentId)
    }
}