package com.globalhiddenodds.androidtestzemoga.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import androidx.work.WorkInfo
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.ui.adapters.ViewPagerAdapter
import com.globalhiddenodds.androidtestzemoga.ui.utils.Utils
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.CrudDatabaseViewModel
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.DownPostsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : Fragment() {
    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var pagerPost: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val downPostsViewModel: DownPostsViewModel by viewModels()
    private val crudViewModel: CrudDatabaseViewModel by activityViewModels()
    private lateinit var loading: ProgressBar

    private val postsPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            tabLayout.tabTextColors = ColorStateList.valueOf(requireActivity().getColor(R.color.white))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_posts,
            container, false
        )
        observerResultViewModelDatabase()
        observerWorkInfo()
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.like).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                if (PostPageFragment.countRecords == 0){
                    Log.i(tag, "DOWNLOAD DATA")
                    downPostsViewModel.downPosts()
                }
                else {
                    showMessageTask(getString(R.string.lbl_delete_records))
                }
                true
            }
            R.id.like -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerPost = view.findViewById(R.id.vp_posts)
        pagerPost.adapter = viewPagerAdapter
        tabLayout = view.findViewById(R.id.tb_post)
        TabLayoutMediator(tabLayout, pagerPost) { tab, position ->
            tab.text = (pagerPost.adapter as ViewPagerAdapter).postFragmentsNames[position]
        }.attach()
        pagerPost.registerOnPageChangeCallback(postsPageChangeCallback)
        loading = view.findViewById(R.id.loading)

    }

    override fun onStop() {
        super.onStop()
        pagerPost.adapter = null

    }

    private fun observerWorkInfo() {
        downPostsViewModel.outputWorkInfo.observe(viewLifecycleOwner,
            Observer { listWorkInfo ->
                listWorkInfo ?: return@Observer
                if (listWorkInfo.isNotEmpty()) {
                    val workInfo = listWorkInfo[0]
                    when {
                        workInfo.state.isFinished -> {
                            showWorkFinished()
                        }
                        workInfo.state == WorkInfo.State.FAILED -> {
                            showWorkError()
                        }
                        else -> {
                            showWorkInProgress()
                        }
                    }

                }
            }
        )
    }

    private fun observerResultViewModelDatabase() {
        crudViewModel.taskResult.observe(viewLifecycleOwner,
            Observer { taskResult ->
                taskResult ?: return@Observer
                showMessageTask(taskResult)
            }
        )
    }

    override fun onDestroy() {
        pagerPost.unregisterOnPageChangeCallback(postsPageChangeCallback)
        super.onDestroy()
    }

    private fun showWorkInProgress() {
        loading.visibility = View.VISIBLE
    }

    private fun showWorkFinished() {
        loading.visibility = View.GONE
        crudViewModel.insertToDatabase()
    }

    private fun showWorkError() {
        loading.visibility = View.GONE
        Utils.notify(requireContext(), getString(R.string.lbl_download_failed))
    }

    private fun showMessageTask(msg: String){
        Utils.notify(requireContext(), msg);
    }
}