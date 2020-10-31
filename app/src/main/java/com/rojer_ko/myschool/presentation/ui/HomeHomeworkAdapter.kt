package com.rojer_ko.myschool.presentation.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rojer_ko.myschool.R
import com.rojer_ko.myschool.data.currentDateTime
import com.rojer_ko.myschool.data.homeworkWarningTime
import com.rojer_ko.myschool.data.model.Homework
import kotlinx.android.extensions.LayoutContainer
import java.util.concurrent.TimeUnit

class HomeHomeworkAdapter(private var items: List<Homework>): RecyclerView.Adapter<HomeHomeworkAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_homework, parent, false)
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
        private val subjectName = containerView.findViewById<TextView>(R.id.subjectHomework)
        private val timeHomeworkImage = containerView.findViewById<ImageView>(R.id.timeHomeworkImage)
        private val daysLeftForHomework = containerView.findViewById<TextView>(R.id.daysLeftForHomework)
        private val homeworkDescription = containerView.findViewById<TextView>(R.id.homeworkDescription)

        fun bind(data: Homework) {
            subjectName.text = data.subject.title
            val daysLeftForHomeworkValue= ((data.deadLine - currentDateTime)/TimeUnit.DAYS.toMillis(1))
            var daysLeftForHomeworkText = ""

            if(daysLeftForHomeworkValue <= homeworkWarningTime){
                timeHomeworkImage.setColorFilter(Color.RED)
                daysLeftForHomework.setTextColor(Color.RED)
            }
            daysLeftForHomeworkText = if(daysLeftForHomeworkValue <= 0){
                "0 " + containerView.context.getString(R.string.string_days_left)
            } else {
                when(daysLeftForHomeworkValue.toInt()){
                    1 -> "1 " + containerView.context.getString(R.string.string_day_left)
                    else -> "$daysLeftForHomeworkValue " + containerView.context.getString(R.string.string_days_left)
                }
            }

            daysLeftForHomework.text = daysLeftForHomeworkText
            homeworkDescription.text = data.description
        }
    }

    fun setData(data: List<Homework>){
        this.items = data
        notifyDataSetChanged()
    }
}