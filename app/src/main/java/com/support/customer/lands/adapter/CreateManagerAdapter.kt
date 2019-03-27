package com.support.customer.lands.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.model.LinkResponse
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.extensions.fromUrlFixData
import kotlinx.android.synthetic.main.item_create_manager.view.*


class CreateManagerAdapter(private var listMyCourse: ArrayList<LinkResponse>?) : RecyclerView.Adapter<CreateManagerAdapter.ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_create_manager, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder?.bindItem(listMyCourse?.get(position))

        holder?.itemView?.rippleItem?.setOnClickListener{
            ShowAlert.confirm(pContext = holder?.itemView.context, message = holder.itemView.context.getString(R.string.text_confirm_delete_image), onClick = {
                position?.let { listMyCourse?.removeAt(it) }
                notifyDataSetChanged()
            })

        }
    }

    override fun getItemCount(): Int {
        return listMyCourse?.size ?: 0
    }

    
    
    class ProjectViewHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindItem(response: LinkResponse?) {
            itemView.image.fromUrlFixData(url = response?.url?.trim(), placeHolder = R.drawable.ic_logo)
        }
    }
}