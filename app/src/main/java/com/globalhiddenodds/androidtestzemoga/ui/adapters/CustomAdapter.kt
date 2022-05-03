package com.globalhiddenodds.androidtestzemoga.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalhiddenodds.androidtestzemoga.databinding.TextRowItemBinding
import com.globalhiddenodds.androidtestzemoga.ui.data.CommentView

// Pattern adapter
class CustomAdapter: ListAdapter<CommentView, CustomAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentView){
            binding.commentView = comment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(TextRowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    override fun submitList(list: List<CommentView>?) {
        super.submitList(list)
        if(list != null){
            Log.i("RECYCLER LIST", list.size.toString())
        }

    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CommentView>() {
            override fun areItemsTheSame(oldComment: CommentView, newComment: CommentView): Boolean {
                return oldComment.commId == newComment.commId
            }

            override fun areContentsTheSame(oldComment: CommentView, newComment: CommentView): Boolean {
                return oldComment.email == newComment.email
            }
        }
    }

}