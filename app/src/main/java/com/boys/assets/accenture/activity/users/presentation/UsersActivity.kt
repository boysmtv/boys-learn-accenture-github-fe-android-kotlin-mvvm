package com.boys.assets.accenture.activity.users.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.boys.assets.accenture.R
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.fragment.popular.vm.PopularViewModel
import com.boys.assets.accenture.activity.users.model.UsersModel
import com.boys.assets.accenture.activity.users.vm.UsersViewModel
import com.boys.assets.accenture.databinding.ActivityUsersBinding
import com.boys.assets.accenture.helper.InterfaceDialog
import com.boys.assets.accenture.utils.LogUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private val thisContext = this@UsersActivity

    private val usersViewModel by viewModel<UsersViewModel>()
    private val popularVM by viewModel<PopularViewModel>()

    private lateinit var binding            : ActivityUsersBinding
    private lateinit var interfaceDialog    : InterfaceDialog

    private lateinit var reposAdapter       : ReposAdapter
    private lateinit var model              : UsersModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding             = ActivityUsersBinding.inflate(layoutInflater)

        interfaceDialog     = InterfaceDialog(thisContext)
        reposAdapter        = ReposAdapter()
        model               = UsersModel()

        setContentView(binding.root)
        supportActionBar!!.hide()

        getIntentExtra()
        setAddressVM(usersViewModel, binding, reposAdapter)
        setRequest(usersViewModel, binding, reposAdapter)
        setOnClick(usersViewModel, binding, reposAdapter)
    }

    /**
     * set view model
     */
    private fun setAddressVM(
        VM: UsersViewModel,
        binding: ActivityUsersBinding,
        reposAdapter: ReposAdapter
    ) {
        with(VM){
            onSuccessUsers.observe(thisContext) {
                setProfile(it, binding, VM)
            }
            onErrorUsers.observe(thisContext) {
                interfaceDialog.dismisDialogLoading()
                val confirmDialog = interfaceDialog.showDialogConfirmWarning("Warning!", "Failed get profile")
                confirmDialog.setConfirmClickListener {
                    confirmDialog.dismiss()
                }
                confirmDialog.show()
            }
            onSuccessRepo.observe(thisContext) {
                reposAdapter.provided(it, thisContext, interfaceDialog)
            }
            onErrorRepo.observe(thisContext) {
                interfaceDialog.dismisDialogLoading()
                val confirmDialog = interfaceDialog.showDialogConfirmWarning("Warning!", "Failed get repos")
                confirmDialog.setConfirmClickListener {
                    confirmDialog.dismiss()
                }
                confirmDialog.show()
            }
        }
    }

    private fun setProfile(model: UsersModel, binding: ActivityUsersBinding, VM: UsersViewModel) {
        interfaceDialog.dismisDialogLoading()

        binding.tvUsersName.text = model.name
        binding.tvUsersLogin.text = "@${model.login}"
        binding.tvUsersFollowers.text = model.followers.toString()
        binding.tvUsersFollowing.text = model.following.toString()
        binding.tvUsersCompany.text = model.company ?: "This is default cause the field is null"
        binding.tvUsersLocation.text = model.location ?: "This is default cause the field is null"
        binding.tvUsersEmail.text = model.email ?: "This is default cause the field is null"
        Glide.with(binding.root).load(model.avatar_url).into(binding.icPhoto)

        binding.rvUsers.adapter = reposAdapter
        interfaceDialog.showDialogLoading("Loading ...")

        VM.getRepo(this.model.login)
    }

    private fun setRequest(
        VM: UsersViewModel,
        binding: ActivityUsersBinding,
        reposAdapter: ReposAdapter
    ) {
        // set loading on ui
        interfaceDialog.showDialogLoading("Loading ...")

        VM.getUser(model.login)
    }

    private fun setOnClick(
        VM: UsersViewModel,
        binding: ActivityUsersBinding,
        reposAdapter: ReposAdapter
    ) {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivFavorite.setOnClickListener {
            val addModel = Users()
            addModel.id = model.id
            addModel.login = model.login
            addModel.avatar_url = model.avatar_url
            addModel.url = model.url

            val userLocal = popularVM.getID(model.id!!)
            if (userLocal > 0){
                binding.ivFavorite.setBackgroundResource(R.drawable.ic_fav)
                popularVM.delFav(addModel)
                Toast.makeText(this, "Your remove this repository", Toast.LENGTH_SHORT)
            }else{
                binding.ivFavorite.setBackgroundResource(R.drawable.ic_fav_true)
                popularVM.addFav(addModel)
                Toast.makeText(this, "Your add this repository", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun getIntentExtra(){
        val intent = intent
        model.id = intent.getIntExtra("id", 0)
        model.login = intent.getStringExtra("login")
        model.avatar_url = intent.getStringExtra("avatar_url")
        model.url = intent.getStringExtra("url")

        val allData = popularVM.getAll()
        if (allData.isNotEmpty()){
            for (element in allData)
                if (model.id == element.id && model.login.equals(element.login) && model.avatar_url.equals(element.avatar_url)){
                    binding.ivFavorite.setBackgroundResource(R.drawable.ic_fav_true)
                    break
                }
        }
    }
}