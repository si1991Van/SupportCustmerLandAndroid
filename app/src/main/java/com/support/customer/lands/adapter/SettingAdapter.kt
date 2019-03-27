package com.support.customer.lands.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import com.support.customer.lands.R
import com.support.customer.lands.model.SettingResponse
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.item_setting.view.*


class SettingAdapter(private var list: ArrayList<SettingResponse>?) : RecyclerView.Adapter<SettingAdapter.ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_setting, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder?.bindItem(list?.get(position))

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
    
    
    class ProjectViewHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindItem(response: SettingResponse?) {
            itemView.sSetting.text = response?.name
            itemView.sSetting.isChecked = response?.status!!

            itemView.sSetting.setOnCheckedChangeListener { buttonView, isChecked ->
                response?.status = isChecked
            }

            itemView.sSetting.setOnClickListener {
                SettingViewModel().postSetting(response?.id, response?.status, onSuccess = {
                }, onFailed = {
                    ShowAlert.fail(pContext = itemView.context, message = it)
                })
            }
        }
    }
}