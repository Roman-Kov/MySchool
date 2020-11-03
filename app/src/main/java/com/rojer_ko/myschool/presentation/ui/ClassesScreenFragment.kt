package com.rojer_ko.myschool.presentation.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myschool.R
import com.rojer_ko.myschool.data.currentDateTime
import com.rojer_ko.myschool.presentation.viewmodel.ClassesScreenViewModel
import com.rojer_ko.myschool.utils.convertSchoolClassesToClassesContainer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_classes_screen.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ClassesScreenFragment : BaseFragment() {
    override val res: Int = R.layout.fragment_classes_screen
    override val resToolbar: Int = R.id.classesToolbar
    private val viewModel: ClassesScreenViewModel by viewModel()
    private val onItemClickListener =
        View.OnClickListener { startSkype() }

    override fun onStart() {
        super.onStart()

        setCurrentDate()
        viewModel.getClassesForDate(currentDateTime)
        observeClasses()
    }

    private fun setCurrentDate() {
        val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

        val today = getString(R.string.today) + dateFormat.format(currentDateTime)
        todayTextView.text = today
    }

    private fun observeClasses(){
        viewModel.classesLiveData.observe(viewLifecycleOwner,
            {

                val data = convertSchoolClassesToClassesContainer(it, onItemClickListener)
                initRecyclerView(data)
            })
    }

    private fun initRecyclerView(items : List<ClassesContainer>){

        val  groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        classesRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter  = groupAdapter
        }
    }
}