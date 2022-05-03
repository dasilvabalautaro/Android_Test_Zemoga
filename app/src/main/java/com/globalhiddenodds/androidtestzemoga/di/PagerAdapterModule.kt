package com.globalhiddenodds.androidtestzemoga.di

import android.content.Context
import com.globalhiddenodds.androidtestzemoga.ui.adapters.ViewPagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object PagerAdapterModule {
    @Provides
    @ActivityScoped
    fun provideAdapterFragmentState(@ActivityContext context: Context): ViewPagerAdapter {
        return ViewPagerAdapter(context)
    }
}