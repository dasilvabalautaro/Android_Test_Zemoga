package com.globalhiddenodds.androidtestzemoga.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.databinding.FragmentPageFavoritesBinding
import com.globalhiddenodds.androidtestzemoga.ui.adapters.PostAdapter
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.CrudDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesPageFragment: Fragment() {
    private var _binding: FragmentPageFavoritesBinding? = null
    private val binding get() = _binding!!
    private val crudViewModel: CrudDatabaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPageFavoritesBinding.inflate(
            inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = crudViewModel

        val adapter = PostAdapter{
            val bundle = bundleOf("userId" to it.id)
            this.findNavController().navigate(R.id.next_action_detail, bundle)
        }
        binding.rvFavorites.adapter = adapter
    }

}