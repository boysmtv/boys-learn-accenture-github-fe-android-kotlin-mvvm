package com.boys.assets.accenture.activity.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.boys.assets.accenture.R
import com.boys.assets.accenture.activity.fragment.adapter.MyPagerAdapter
import com.boys.assets.accenture.activity.fragment.popular.presentation.PopularFragment
import com.boys.assets.accenture.databinding.FragmentHomeBinding


class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val TAG = this::class.java.simpleName

    private val mAdapterViewPager = MyPagerAdapter(supportFragmentManager)
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpHome.adapter = mAdapterViewPager
        binding.vpHome.addOnPageChangeListener(this@HomeActivity)
        mAdapterViewPager.notifyDataSetChanged()

        binding.ivSearch.setOnClickListener {
            val mFragment = PopularFragment()
            val mBundle = Bundle()
            mBundle.putString("search", binding.etSearch.text.toString())
            mFragment.arguments = mBundle

            val transaction: FragmentTransaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, mFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val fragment: Fragment = mAdapterViewPager.getFragment(position)!!
        if (fragment != null) {
            fragment.onResume()
        }
    }

    override fun onPageSelected(position: Int) {
        val fragment: Fragment = mAdapterViewPager.getFragment(position)!!
        if (fragment != null) {
            fragment.onResume()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}