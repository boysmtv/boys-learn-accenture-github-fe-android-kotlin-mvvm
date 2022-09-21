package com.boys.assets.accenture.activity.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.boys.assets.accenture.activity.fragment.popular.vm.PopularViewModel
import com.boys.assets.accenture.databinding.FragmentPopularBinding
import com.boys.assets.accenture.helper.InterfaceDialog
import com.boys.assets.accenture.utils.LogUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val TAG = this::class.java.simpleName
    private val popularVM by viewModel<PopularViewModel>()

    private lateinit var interfaceDialog: InterfaceDialog
    private lateinit var binding: FragmentPopularBinding
    private val favoriteAdapter = FavoriteAdapter()

    fun newInstance(): FavoriteFragment {
        return FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        LogUtil.e(TAG, "onCreateView")
        binding = FragmentPopularBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interfaceDialog = InterfaceDialog(requireContext())

        LogUtil.e(TAG, "onViewCreated")
        setup()
    }

    private fun setup(){
        binding.tvPopular.adapter = favoriteAdapter
        refreshData()
    }

    private fun refreshData(){
        popularVM.getAll().let { it1 -> favoriteAdapter.provided(it1, requireContext(), interfaceDialog, popularVM) }
        favoriteAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e(TAG, "onResume")
        refreshData()
    }
}