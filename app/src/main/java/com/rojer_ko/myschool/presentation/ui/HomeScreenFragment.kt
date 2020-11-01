package com.rojer_ko.myschool.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myschool.R
import com.rojer_ko.myschool.data.currentDateTime
import com.rojer_ko.myschool.data.model.Homework
import com.rojer_ko.myschool.data.model.SchoolClass
import com.rojer_ko.myschool.data.studentName
import com.rojer_ko.myschool.presentation.viewmodel.HomeScreenViewModel
import com.rojer_ko.myschool.utils.getCurrentSchoolClass
import com.rojer_ko.myschool.utils.showToast
import kotlinx.android.synthetic.main.fragment_home_screen.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeScreenFragment : BaseFragment() {

    override val res: Int = R.layout.fragment_home_screen
    override val resToolbar: Int = R.id.homeToolbar

    private val viewModel:HomeScreenViewModel by viewModel()
    private var homeClassesAdapter: HomeClassesAdapter? = null
    private var homeHomeworkAdapter: HomeHomeworkAdapter? = null

    private val onItemClickListener: HomeClassesAdapter.OnListItemClickListener =
        object : HomeClassesAdapter.OnListItemClickListener {
            override fun onItemClick() {
                startSkype()
            }
        }

    override fun onStart() {
        super.onStart()

        showGreeting()
        viewModel.getExamTimer()
        viewModel.getClassesForDate(currentDateTime)
        viewModel.getHomework()
        observeTimer()
        observeClasses()
        observeHomework()
    }

    private fun setDataToClassesAdapter(data: List<SchoolClass>) {
        if (homeClassesAdapter != null) {
            homeClassesAdapter!!.setData(data)
        } else {
            homeClassesAdapter = HomeClassesAdapter(onItemClickListener, data)
        }

        if(classesRecyclerView.adapter == null) {
            classesRecyclerView.adapter = homeClassesAdapter
            classesRecyclerView.layoutManager = LinearLayoutManager(
                activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false
            )
        }

        if(classesRecyclerView.adapter != null){
            classesRecyclerView.scrollToPosition(getCurrentSchoolClass(data, currentDateTime))
        }
    }

    private fun setDataToHomeworkAdapter(data: List<Homework>) {
        if (homeHomeworkAdapter != null) {
            homeHomeworkAdapter!!.setData(data)
        } else {
            homeHomeworkAdapter = HomeHomeworkAdapter(data)
        }

        if(homeworkRecyclerView.adapter == null) {
            homeworkRecyclerView.adapter = homeHomeworkAdapter
            homeworkRecyclerView.layoutManager = LinearLayoutManager(
                activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false
            )
        }
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

    private fun observeClasses(){
        viewModel.classesLiveData.observe(viewLifecycleOwner,
            {
                val classesTodayText = if (it.size != 1) {
                    it.size.toString() + getString(R.string.classes_today_text)
                } else {
                    it.size.toString() + getString(R.string.class_today_text)
                }
                classesTodayTextView.text = classesTodayText

                setDataToClassesAdapter(it)
            })
    }

    private fun observeHomework(){
        viewModel.homeWorksLiveData.observe(viewLifecycleOwner,{
            setDataToHomeworkAdapter(it)
        })
    }

    private fun startSkype(){
        try {
            val skype = Intent("android.intent.action.VIEW")
            skype.data = Uri.parse("skype:")
            startActivity(skype)
        } catch (e: ActivityNotFoundException) {
            showToast("Skype not found")
        }
    }
}