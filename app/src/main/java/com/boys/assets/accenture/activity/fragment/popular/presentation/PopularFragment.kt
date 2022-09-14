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

    private var query = ""

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

        // Gets the data from the passed bundle
        val bundle = arguments
        if (bundle != null){
            LogUtil.e(TAG, "bundle")
            val msg = bundle.getString("search")
            if (!msg.isNullOrBlank()){
                if (!msg.isNullOrEmpty()){
                    query = msg
                }
            }
        }
        setNews()
    }

    private fun setNews() {
//        q = "tom+repos:>42+followers:>1000",
        val reqModel = PopularReqModel(
            q = query,
            sort = "stars",
            per_page = 20,
            page = 1
        )

        if (requireContext().isNetworkAvailable()) {
            interfaceDialog.showDialogLoading("loading ..")
            if (query != ""){
                popularVM.doIt(reqModel)
            }else{
                popularVM.doIt()
            }
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        }

        with(popularVM) {
            onSuccess.observe(viewLifecycleOwner) {
                listModel = it
                setup()
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

    private fun setup(){
        val popularAdapter = PopularAdapter()
        binding.tvPopular.isSaveEnabled = false
        binding.tvPopular.adapter = popularAdapter
        popularAdapter.provided(listModel, requireContext(), interfaceDialog, popularVM)
    }
}