package com.globalhiddenodds.androidtestzemoga.ui.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.domain.CommentsApiStatus
import com.globalhiddenodds.androidtestzemoga.ui.data.CommentView
import com.globalhiddenodds.androidtestzemoga.ui.data.UserView

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<UserView>?){
    val adapter = recyclerView.adapter as PostAdapter
    adapter.submitList(data)
}

@BindingAdapter("listComment")
fun bindRecyclerViewComment(recyclerView: RecyclerView, data: List<CommentView>?){
    val adapter = recyclerView.adapter as CustomAdapter
    adapter.submitList(data)
}

@BindingAdapter("like")
fun bindImage(imageView: ImageView, like: Boolean){
    if (like){
        imageView.setImageResource(R.drawable.ic_start_round)
    }
    else{
        imageView.setImageResource(R.drawable.ic_person_round)
    }
}

@BindingAdapter("status")
fun bindStatus(statusImageView: ImageView, status: Int?){
    if(status != null){
        when(status){
            2 -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.ic_done_all)
            }
            0 -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
            1 -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
            }
            else -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
        }

    }
}