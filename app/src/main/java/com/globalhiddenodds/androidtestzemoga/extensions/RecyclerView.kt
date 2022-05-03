package com.globalhiddenodds.androidtestzemoga.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.globalhiddenodds.androidtestzemoga.R

@SuppressLint("UseCompatLoadingForDrawables")
fun addDecorationRecycler(rv: RecyclerView, context: Context){
    val horizontalDecoration =
        DividerItemDecoration(rv.context,
            DividerItemDecoration.VERTICAL)
    val horizontalDivider: Drawable? = context
        .getDrawable(R.drawable.horizontal_divider)
    if (horizontalDivider != null) {
        horizontalDecoration.setDrawable(horizontalDivider)
    }
    rv.addItemDecoration(horizontalDecoration)
}