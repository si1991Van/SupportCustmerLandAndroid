package com.support.customer.lands.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.utills.extensions.fromUrlFixData
import kotlinx.android.synthetic.main.item_project.view.*


class ProjectAdapter(private var listMyCourse: ArrayList<ProjectResponse>?, private var onClick: (ProjectResponse?) -> Unit) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_project, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder?.bindItem(listMyCourse?.get(position))

        holder?.itemView?.rippleItem?.setOnRippleCompleteListener{
            onClick(listMyCourse?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return listMyCourse?.size ?: 0
    }

    
    
    class ProjectViewHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindItem(response: ProjectResponse?) {
            itemView.image.fromUrlFixData(url = response?.image_url, placeHolder = R.drawable.ic_logo)
        }
    }
}