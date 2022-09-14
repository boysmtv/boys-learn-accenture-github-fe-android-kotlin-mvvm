package com.boys.assets.accenture.activity.users.presentation

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boys.assets.accenture.activity.users.model.ReposModel
import com.boys.assets.accenture.databinding.ActivityUsersListItemBinding
import com.boys.assets.accenture.helper.InterfaceDialog
import com.bumptech.glide.Glide

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.AddressHolder>() {

    private var listModel = mutableListOf<ReposModel>()

    fun provided(
        model: ArrayList<ReposModel>,
        context: Activity,
        interfaceDialog: InterfaceDialog
    ) {
        this.listModel = model.toMutableList()

        interfaceDialog.dismisDialogLoading()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposAdapter.AddressHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ActivityUsersListItemBinding.inflate(inflater, parent, false)
        return AddressHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.listModel.size
    }

    override fun onBindViewHolder(holder: ReposAdapter.AddressHolder, position: Int) {
        val model = this.listModel[position]
        holder.bind(model)
    }

    inner class AddressHolder(binding: ActivityUsersListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val thisBinding: ActivityUsersListItemBinding

        fun bind(model: ReposModel) {
            thisBinding.tvReposName.text = model.full_name
            thisBinding.tvReposDesc.text = model.description
            thisBinding.tvReposWatchers.text = model.watchers.toString()
            thisBinding.tvReposUpdated.text = model.updated_at

            Glide.with(thisBinding.root).load(model.owner?.avatar_url).into(thisBinding.icPhoto)
        }

        init {
            thisBinding = binding
        }
    }
}