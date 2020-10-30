package com.rojer_ko.myschool.presentation.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.rojer_ko.myschool.R
import com.rojer_ko.myschool.data.studentName
import com.rojer_ko.myschool.presentation.viewmodel.HomeScreenViewModel
import kotlinx.android.synthetic.main.fragment_home_screen.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeScreenFragment : Fragment() {

    private val viewModel:HomeScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.let {
            val toolbar: Toolbar = it.findViewById(R.id.homeToolbar)
            (activity as MainActivity).setSupportActionBar(toolbar)
        }
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onStart() {
        super.onStart()

        showGreeting()
        viewModel.getExamTimer()
        observeTimer()
    }

    private fun showGreeting() {
        val greetingConst =
            getString(R.string.string_hello) + getString(R.string.string_coma_whitespace)
        val greetingName = studentName
        val greeting = SpannableString(greetingConst + greetingName)
        greeting.setSpan(
            StyleSpan(Typeface.BOLD),
            greetingConst.length,
            (greetingConst + greetingName).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        greetingTextView.text = greeting
    }

    private fun observeTimer(){
        viewModel.nextExamTimerLiveDataLiveData.observe(viewLifecycleOwner,
            {
                firstHour.text = it.firstHour
                secondHour.text = it.secondHour
                firstMinute.text = it.firstMinute
                secondMinute.text = it.secondMinute
                firstSecond.text = it.firstSecond
                secondSecond.text = it.secondSecond
            })
    }
}