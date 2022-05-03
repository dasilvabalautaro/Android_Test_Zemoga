package com.globalhiddenodds.androidtestzemoga.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.databinding.FragmentDetailBinding
import com.globalhiddenodds.androidtestzemoga.extensions.addDecorationRecycler
import com.globalhiddenodds.androidtestzemoga.ui.adapters.CustomAdapter
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.CrudDatabaseViewModel
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.GetCommentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val getCommentsViewModel: GetCommentsViewModel by viewModels()
    private val crudViewModel: CrudDatabaseViewModel by activityViewModels()
    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(
            inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getInt("userId")!!
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelComments = getCommentsViewModel

        val adapter = CustomAdapter()
        addDecorationRecycler(binding.rvComments, requireContext())
        binding.rvComments.adapter = adapter

        crudViewModel.user.observe(this.viewLifecycleOwner) {user ->
            user.let {
                requireActivity().runOnUiThread{
                    binding.txtEmail.text = it.email
                    binding.txtName.text = it.name
                    binding.txtPhone.text = it.phone
                    binding.txtWebsite.text = it.website
                }
            }
        }
        crudViewModel.company.observe(this.viewLifecycleOwner) {company ->
            company.let {
                val description = "Company: ${company.name}\nCatchPhrase: ${company.catchPhrase}\nBs: ${company.bs}"
                binding.textDescription.text = description
            }
        }

        if (userId != 0){
            getCommentsViewModel.getCommentsById(userId)
            crudViewModel.getUser(userId)
            crudViewModel.getCompany(userId)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.refresh).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                true
            }
            R.id.like -> {
                if(userId != 0){
                    crudViewModel.updateLike(userId, true)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}