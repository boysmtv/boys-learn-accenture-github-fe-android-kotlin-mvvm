package com.boys.assets.accenture.activity.fragment.popular.presentation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boys.assets.accenture.R
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.fragment.popular.vm.PopularViewModel
import com.boys.assets.accenture.activity.users.presentation.UsersActivity
import com.boys.assets.accenture.databinding.FragmentPopularListItemBinding
import com.boys.assets.accenture.helper.InterfaceDialog
import com.boys.assets.accenture.utils.LogUtil
import com.bumptech.glide.Glide


class PopularAdapter: RecyclerView.Adapter<PopularAdapter.AddressHolder>() {

    private val TAG = this::class.java.simpleName

    private var itemList = mutableListOf<Users>()
    private lateinit var context: Context
    private lateinit var popularVM : PopularViewModel

    fun provided(
        model: List<Users>,
        context: Context,
        interfaceDialog: InterfaceDialog,
        popularVM: PopularViewModel
    ) {
        this.itemList = model.toMutableList()
        this.context = context
        this.popularVM = popularVM

        interfaceDialog.dismisDialogLoading()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.AddressHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPopularListItemBinding.inflate(inflater, parent, false)
        return AddressHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PopularAdapter.AddressHolder, position: Int) {
        val model = itemList[position]
        holder.bind(position, model)
    }

    inner class AddressHolder(binding: FragmentPopularListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val thisBinding: FragmentPopularListItemBinding
        init {
            thisBinding = binding
        }

        fun bind(
            position: Int,
            model: Users,
        ) {
            LogUtil.e(TAG, "position : $position")
            LogUtil.e(TAG, "element : ${model.id}")
            thisBinding.tvSearchName.text = model.login
            thisBinding.tvUrl.text = model.url

            Glide.with(thisBinding.root).load(model.avatar_url).into(thisBinding.icPhoto)

            val allData = popularVM.getAll()
            if (allData.isNotEmpty()){
                for (element in allData)
                    if (model.url == element.url && model.login == element.login){
                        thisBinding.ivFavorite.setBackgroundResource(R.drawable.ic_fav_true)
                        LogUtil.e(TAG, "model : ${model.url}, element: ${element.url}")
                        LogUtil.e(TAG, "model : ${model.login}, element: ${element.login}")
                        break
                    }
            }

            thisBinding.layoutContent.setOnClickListener {
                val intent = Intent(context, UsersActivity::class.java)
                intent.putExtra("id", model.id)
                intent.putExtra("login", model.login)
                intent.putExtra("avatar_url", model.avatar_url)
                intent.putExtra("url", model.url)
                context.startActivity(intent)
            }

            thisBinding.ivFavorite.setOnClickListener {
                val addModel = Users()
                    addModel.id = model.id
                    addModel.login = model.login
                    addModel.avatar_url = model.avatar_url
                    addModel.url = model.url

                val userLocal = popularVM.getID(model.login!!)
                if (userLocal > 0){
                    popularVM.delFav(addModel)
                    thisBinding.ivFavorite.setBackgroundResource(R.drawable.ic_fav)
                    notifyDataSetChanged()
                }else{
                    popularVM.addFav(addModel)
                    thisBinding.ivFavorite.setBackgroundResource(R.drawable.ic_fav_true)
                    notifyDataSetChanged()
                }
            }
        }
    }
}