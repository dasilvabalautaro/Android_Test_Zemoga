package com.globalhiddenodds.androidtestzemoga.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.databinding.PostViewItemBinding
import com.globalhiddenodds.androidtestzemoga.ui.data.UserView
import com.globalhiddenodds.androidtestzemoga.ui.fragments.PostPageFragment

class PostAdapter(private val onItemClicked: (UserView) -> Unit):
    ListAdapter<UserView, PostAdapter.PostViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PostViewHolder {
        return PostViewHolder(
            PostViewItemBinding
            .inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postView = getItem(position)
        holder.itemView.setOnClickListener {
            if(listSelect.contains(postView.id)){
                it.setBackgroundColor(Color.WHITE)
                listSelect.remove(postView.id)
            }
            else {
                it.setBackgroundColor(Color.GREEN)
                listSelect.add(postView.id)
            }
            onItemClicked(postView)
        }
        holder.bind(postView)
    }

    override fun submitList(list: List<UserView>?) {
        super.submitList(list)
        if (list != null){
            PostPageFragment.countRecords = list.size
        }
        else {
            PostPageFragment.countRecords = 0
        }
    }
    class PostViewHolder(private var binding: PostViewItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(userView: UserView){
            binding.userView = userView
            binding.ibNext.setOnClickListener {
                val bundle = bundleOf("userId" to userView.userId)
                it.findNavController().navigate(R.id.next_action_detail, bundle)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val listSelect = mutableListOf<Int>()
        private val DiffCallback = object : DiffUtil.ItemCallback<UserView>() {
            override fun areItemsTheSame(oldPostView: UserView, newPostView: UserView): Boolean {
                return oldPostView.id == newPostView.id
            }

            override fun areContentsTheSame(oldPostView: UserView, newPostView: UserView): Boolean {
                return oldPostView.title == newPostView.title
            }
        }
    }
}