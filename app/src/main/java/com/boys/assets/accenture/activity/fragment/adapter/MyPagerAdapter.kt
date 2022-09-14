package com.boys.assets.accenture.activity.fragment.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import androidx.fragment.app.FragmentPagerAdapter
import com.boys.assets.accenture.activity.fragment.favorite.FavoriteFragment
import com.boys.assets.accenture.activity.fragment.popular.presentation.PopularFragment

class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val mFragmentTags: MutableMap<Int, String>
    private val mFragmentManager: FragmentManager

    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment().newInstance()
            else -> FavoriteFragment().newInstance()
        }
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Popular"
            1 -> "Favorite"
            else -> "Tab"
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val `object` = super.instantiateItem(container, position)
        if (`object` is Fragment) {
            val fragment: Fragment = `object`
            val tag: String = fragment.tag.toString()
            mFragmentTags[position] = tag
        }
        return `object`
    }

    fun getFragment(position: Int): Fragment? {
        var fragment: Fragment? = null
        val tag = mFragmentTags[position]
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag)
        }
        return fragment
    }

    companion object {
        private const val NUM_ITEMS = 2
    }

    init {
        mFragmentManager = fragmentManager
        mFragmentTags = HashMap()
    }
}