package com.globalhiddenodds.androidtestzemoga.ui.adapters

import android.content.Context
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.globalhiddenodds.androidtestzemoga.ui.fragments.FavoritesPageFragment
import com.globalhiddenodds.androidtestzemoga.ui.fragments.PostPageFragment
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ViewPagerAdapter @Inject constructor(
    @ActivityContext context: Context):
    FragmentStateAdapter(context as AppCompatActivity) {

    val postFragmentsNames = arrayOf(
        "All",
        "Favorites"
    )

    private val pages = SparseArray<Fragment>()

    init {
        addFragment()
    }

    private fun addFragment(){
        pages.put(0, PostPageFragment())
        pages.put(1, FavoritesPageFragment())
    }
    override fun getItemCount(): Int {
        return pages.size()
    }

    override fun createFragment(position: Int): Fragment {
        return pages.get(position)
    }
}