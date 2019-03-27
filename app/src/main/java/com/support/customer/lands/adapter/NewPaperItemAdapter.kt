package com.support.customer.lands.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.model.NewsPaperItemResponse
import com.support.customer.lands.utills.extensions.fromUrlFixData
import kotlinx.android.synthetic.main.item_news_paper.view.*


class NewPaperItemAdapter(private var listMyCourse: ArrayList<NewsPaperItemResponse>?, private var onClick: (NewsPaperItemResponse?) -> Unit) : RecyclerView.Adapter<NewPaperItemAdapter.NewsPaperViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsPaperViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news_paper, parent, false)
        return NewsPaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsPaperViewHolder, position: Int) {
        holder?.bindItem(listMyCourse?.get(position))

        holder?.itemView?.rippleItem?.setOnRippleCompleteListener{
            onClick(listMyCourse?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return listMyCourse?.size ?: 0
    }

    
    
    class NewsPaperViewHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindItem(response: NewsPaperItemResponse?) {
            itemView.txtName.text = response?.title
            itemView.txtDateTime.text = response?.created_at
            itemView.imgLogo.fromUrlFixData(url = response?.image_url, placeHolder = R.drawable.ic_background_default)
        }
    }
}