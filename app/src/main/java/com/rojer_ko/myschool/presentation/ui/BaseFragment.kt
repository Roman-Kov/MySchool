package com.rojer_ko.myschool.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.rojer_ko.myschool.utils.showToast

abstract class BaseFragment: Fragment() {

    abstract val res: Int
    abstract val resToolbar: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.let {
            setToolbar(it, resToolbar)
        }
        return inflater.inflate(res, container, false)
    }

    private fun setToolbar(view: View, resToolbar: Int){
        val toolbar: Toolbar = view.findViewById(resToolbar)
        (activity as MainActivity).setSupportActionBar(toolbar)
    }

    protected fun startSkype(){
        try {
            val skype = Intent("android.intent.action.VIEW")
            skype.data = Uri.parse("skype:")
            startActivity(skype)
        } catch (e: ActivityNotFoundException) {
            showToast("Skype not found")
        }
    }
}