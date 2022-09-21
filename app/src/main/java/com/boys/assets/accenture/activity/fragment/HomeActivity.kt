package com.boys.assets.accenture.activity.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.boys.assets.accenture.R
import com.boys.assets.accenture.activity.fragment.adapter.MyPagerAdapter
import com.boys.assets.accenture.activity.fragment.popular.model.PopularReqModel
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.fragment.popular.presentation.PopularAdapter
import com.boys.assets.accenture.activity.fragment.popular.vm.PopularViewModel
import com.boys.assets.accenture.databinding.FragmentHomeBinding
import com.boys.assets.accenture.helper.InterfaceDialog
import com.boys.assets.accenture.utils.LogUtil
import com.boys.assets.accenture.utils.isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val TAG = this::class.java.simpleName
    private val popularVM by viewModel<PopularViewModel>()

    private val mAdapterViewPager = MyPagerAdapter(supportFragmentManager)
    private lateinit var binding: FragmentHomeBinding

    private lateinit var interfaceDialog: InterfaceDialog
    private lateinit var listModel : List<Users>
    private val popularAdapter = PopularAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpHome.adapter = mAdapterViewPager
        binding.vpHome.addOnPageChangeListener(this@HomeActivity)
        mAdapterViewPager.notifyDataSetChanged()

        binding.ivSearch.setOnClickListener {
            setNews(binding.etSearch.text.toString().trim())
        }
        setup()
    }
    private fun setNews(query: String) {
        if (isNetworkAvailable()) {
            LogUtil.e(TAG, "query : $query")
            LogUtil.e(TAG, "query.length : ${query.length}")
            if (query.isNotEmpty()){
                //q = "tom+repos:>42+followers:>1000",
                val reqModel = PopularReqModel(
                    q = query,
                    sort = "stars",
                    per_page = 20,
                    page = 1
                )
                interfaceDialog.showDialogLoading("loading ..")
                popularVM.doIt(reqModel)
            }else{
                binding.rvSearch.visibility = View.GONE
                binding.vpHome.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setup(){
        interfaceDialog = InterfaceDialog(this)
        listModel = listOf()
        binding.rvSearch.adapter = popularAdapter

        with(popularVM) {
            onSuccess.observe(this@HomeActivity) {
                interfaceDialog.dismisDialogLoading()
                listModel = it
                if (it.isNotEmpty()){
                    refreshData()
                    binding.rvSearch.visibility = View.VISIBLE
                    binding.vpHome.visibility = View.GONE
                }else{
                    val confirmDialog = interfaceDialog.showDialogConfirmWarning("Warning!", "User not found")
                    confirmDialog.setConfirmClickListener {
                        confirmDialog.dismiss()
                    }
                    confirmDialog.show()
                }
            }
            onError.observe(this@HomeActivity) {
                interfaceDialog.dismisDialogLoading()
                binding.rvSearch.visibility = View.GONE
                binding.vpHome.visibility = View.VISIBLE
                val confirmDialog = interfaceDialog.showDialogConfirmWarning("Warning!", it)
                confirmDialog.setConfirmClickListener {
                    confirmDialog.dismiss()
                }
                confirmDialog.show()
            }
        }
    }

    private fun refreshData(){
        popularAdapter.provided(listModel, this, interfaceDialog, popularVM)
        popularAdapter.notifyDataSetChanged()
    }

    override fun onPageSelected(position: Int) {
        val fragment: Fragment = mAdapterViewPager.getFragment(position)!!
        if (fragment != null) {
            fragment.onResume()
        }
    }
    override fun onPageScrollStateChanged(state: Int) { }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

}