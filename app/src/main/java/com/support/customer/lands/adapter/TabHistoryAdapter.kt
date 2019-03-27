package com.support.customer.lands.adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.support.customer.lands.R
import com.support.customer.lands.model.HistoryResponse
import com.support.customer.lands.model.HistoryTitleResponse
import crm.com.vn.Adapter.View.HistoryItemViewHolder
import crm.com.vn.Adapter.View.HistoryTitleViewHolder


class TabHistoryAdapter(context: Context?, list: List<HistoryTitleResponse>) :
    ExpandableRecyclerAdapter<HistoryTitleResponse, HistoryResponse, HistoryTitleViewHolder, HistoryItemViewHolder>(list) {

    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }

    // onCreate ...
    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): HistoryTitleViewHolder {
        val recipeView = mInflater.inflate(R.layout.item_title_history, parentViewGroup, false)
        return HistoryTitleViewHolder(recipeView)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val ingredientView = mInflater.inflate(R.layout.item_history, childViewGroup, false)
        return HistoryItemViewHolder(ingredientView)
    }

    override fun onBindParentViewHolder(parentTitleViewHolder: HistoryTitleViewHolder, parentPosition: Int, parent: HistoryTitleResponse) {
        parentTitleViewHolder.bind(parent)
    }

    override fun onBindChildViewHolder(
        childItemViewHolder: HistoryItemViewHolder,
        parentPosition: Int,
        childPosition: Int,
        child: HistoryResponse
    ) {
        childItemViewHolder.bind(child)
    }


}