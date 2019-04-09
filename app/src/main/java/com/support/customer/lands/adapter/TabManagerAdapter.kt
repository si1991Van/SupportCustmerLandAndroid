package com.support.customer.lands.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.support.customer.lands.R
import com.support.customer.lands.model.ManagerResponse
import com.support.customer.lands.utills.extensions.statusConsignment
import kotlinx.android.synthetic.main.item_manager.view.*


class TabManagerAdapter(private var listMyCourse: ArrayList<ManagerResponse>?, val onClickEdit: (ManagerResponse?) -> Unit,
                        val onDelete: (ManagerResponse?) -> Unit) :
    RecyclerSwipeAdapter<TabManagerAdapter.ManagerViewHolder>() {



    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_manager, parent, false)
        return ManagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManagerViewHolder, position: Int) {

        holder?.bindItem(listMyCourse?.get(position))
        holder.itemView.swipe.showMode = SwipeLayout.ShowMode.PullOut

        holder.itemView.swipe.addDrag(
            SwipeLayout.DragEdge.Right,
            holder.itemView.swipe.findViewById(R.id.bottom_wraper)
        )

        holder.itemView.Edit.setOnClickListener { view ->
            onClickEdit(listMyCourse?.get(position))
        }

        holder.itemView.Delete.setOnClickListener { v ->
            mItemManger.removeShownLayouts(holder.itemView.swipe)
            notifyDataSetChanged()
            mItemManger.closeAllItems()
            onDelete(listMyCourse?.get(position))
        }

        mItemManger.bindView(holder.itemView, position)

        holder.itemView.swipe.isSwipeEnabled = listMyCourse?.get(position)?.status != 4

    }

    override fun getItemCount(): Int {
        return listMyCourse?.size ?: 0
    }

    class ManagerViewHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindItem(response: ManagerResponse?) {
            itemView.txtTitle.text = response?.title
            itemView.txtDateTime.text = response?.created_at
            if (response?.status == 4){
                itemView.txtStatus.setTextColor(Color.RED)
            }else{
                itemView.txtStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorText21))
            }

            itemView.txtStatus.text = response?.status?.statusConsignment()
        }


    }
}
