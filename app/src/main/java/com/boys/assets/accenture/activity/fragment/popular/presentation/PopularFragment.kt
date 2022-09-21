package com.boys.assets.accenture.activity.fragment.popular.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.boys.assets.accenture.R
import com.boys.assets.accenture.activity.fragment.popular.model.PopularReqModel
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.fragment.popular.vm.PopularViewModel
import com.boys.assets.accenture.databinding.FragmentPopularBinding
import com.boys.assets.accenture.helper.InterfaceDialog
import com.boys.assets.accenture.utils.LogUtil
import com.boys.assets.accenture.utils.isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private val TAG = this::class.java.simpleName
    private val popularVM by viewModel<PopularViewModel>()

    private lateinit var interfaceDialog: InterfaceDialog
    private lateinit var binding: FragmentPopularBinding
    private lateinit var listModel : List<Users>
    private val popularAdapter = PopularAdapter()

    private var isStart = false

    fun newInstance(): PopularFragment {
        return PopularFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interfaceDialog = InterfaceDialog(requireContext())
        listModel = listOf()
        LogUtil.e(TAG, "onViewCreated")

        setup()
        setNews()
    }

    private fun setNews() {
        if (requireContext().isNetworkAvailable()) {
            interfaceDialog.showDialogLoading("loading ..")
            popularVM.doIt()
        } else {
            Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setup(){
        binding.tvPopular.adapter = popularAdapter

        with(popularVM) {
            onSuccess.observe(viewLifecycleOwner) {
                interfaceDialog.dismisDialogLoading()
                listModel = it
                if (it.isNotEmpty()){
                    refreshData()
                }else{
                    val confirmDialog = interfaceDialog.showDialogConfirmWarning("Warning!", "User not found")
                    confirmDialog.setConfirmClickListener {
                        confirmDialog.dismiss()
                    }
                    confirmDialog.show()
                }
            }
            onError.observe(viewLifecycleOwner) {
                interfaceDialog.dismisDialogLoading()
                val confirmDialog = interfaceDialog.showDialogConfirmWarning("Warning!", it)
                confirmDialog.setConfirmClickListener {
                    confirmDialog.dismiss()
                }
                confirmDialog.show()
            }
        }
    }

    private fun refreshData(){
        popularAdapter.provided(listModel, requireContext(), interfaceDialog, popularVM)
        popularAdapter.notifyDataSetChanged()
        isStart = true
    }

    override fun onResume() {
        super.onResume()
        if (isStart)
            refreshData()
    }

}