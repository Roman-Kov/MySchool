package com.rojer_ko.myschool.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rojer_ko.myschool.R
import com.rojer_ko.myschool.data.model.SchoolClass
import kotlinx.android.extensions.LayoutContainer
import java.text.SimpleDateFormat
import java.util.*

class HomeClassesAdapter(private val onItemClickListener: OnListItemClickListener,
                         private var items: List<SchoolClass>): RecyclerView.Adapter<HomeClassesAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_classes, parent, false)
        return MainViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainViewHolder(override val containerView: View):
    RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val subjectName = containerView.findViewById<TextView>(R.id.subjectTextView)
        private val classStartEndTextView = containerView.findViewById<TextView>(R.id.classStartEnd)
        private val openSkypeBtn = containerView.findViewById<View>(R.id.openSkypeBtn)

        fun bind(data: SchoolClass) {

            subjectName.text = data.subject.title
            if(data.isOnline){
                openSkypeBtn.visibility = View.VISIBLE
                openSkypeBtn.setOnClickListener {
                    onItemClickListener.onItemClick()
                }
            }

            val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
            val classStart = dateFormat.format(data.dateTimeOfStart)
            val classEnd = dateFormat.format(data.dateTimeEnd)
            val classStartEnd = "$classStart - $classEnd"
            classStartEndTextView.text = classStartEnd
        }
    }

    interface OnListItemClickListener{
        fun onItemClick()
    }

    fun setData(data: List<SchoolClass>){
        this.items = data
        notifyDataSetChanged()
    }
}