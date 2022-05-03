package com.globalhiddenodds.androidtestzemoga.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.databinding.FragmentPagePostBinding
import com.globalhiddenodds.androidtestzemoga.ui.adapters.PostAdapter
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.CrudDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostPageFragment: Fragment() {
    private var _binding: FragmentPagePostBinding? = null
    private val binding get() = _binding!!
    private val crudViewModel: CrudDatabaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagePostBinding.inflate(
            inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = crudViewModel

        val adapter = PostAdapter{
            userId = it.userId
        }
        binding.rvPosts.adapter = adapter
    }

    companion object{
        var userId = 0
        var countRecords = 0
    }
}