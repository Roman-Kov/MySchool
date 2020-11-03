package com.rojer_ko.myschool.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.rojer_ko.myschool.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_classes_classes.*

class ClassesContainer(
    private val classesItemTime: String,
    private val classesItemSubject: String,
    private val classesItemTeacher: String,
    private val classesItemDescription: String,
    private val isOnline: Boolean,
    private val isExtra: Boolean,
    private val onClick: View.OnClickListener
): Item() {

    override fun getLayout() = R.layout.item_classes_classes

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            classesItemTimeTextView.text = classesItemTime
            classesItemSubjectTextView.text = classesItemSubject
            classesItemTeacherTextView.text = classesItemTeacher
            classesItemDescriptionTextView.text = classesItemDescription

            if(isOnline){
                classesItemOpenSkypeBtn.visibility = View.VISIBLE
                classesItemOpenSkypeBtn.setOnClickListener(onClick)
            }

            if(isExtra){
                classesItemImage.setColorFilter(Color.WHITE)
                classesItemTeacherTextView.setTextColor(Color.WHITE)
                classesItemDescriptionTextView.setTextColor(Color.WHITE)
                classesContainer.background = viewHolder.containerView.context.getDrawable(R.drawable.background_rounded_gradient)
            }
        }
    }
}