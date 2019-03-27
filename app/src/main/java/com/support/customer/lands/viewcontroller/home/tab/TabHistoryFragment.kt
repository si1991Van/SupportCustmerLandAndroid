package com.support.customer.lands.viewcontroller.home.tab

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.adapter.ProjectAdapter
import com.support.customer.lands.adapter.TabHistoryAdapter
import com.support.customer.lands.databinding.FragmentTabHistoryBinding
import com.support.customer.lands.model.HistoryResponse
import com.support.customer.lands.model.HistoryTitleResponse
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewcontroller.home.MainActivity
import com.support.customer.lands.viewmodel.TabHistoryViewModel
import haiphat.com.bds.nghetuvan.view.BaseFragment

class TabHistoryFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var tabHistoryFragment: FragmentTabHistoryBinding
    private var vm = TabHistoryViewModel()
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tabHistoryFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_history, container, false)
        if (userVisibleHint && !isDataLoaded()) {
            getItemHistory()
        }
        (activity as MainActivity).setHeaderTitle(getString(R.string.txt_tab_history_title))
        tabHistoryFragment.swipeRefreshLayout.setOnRefreshListener(this)
        rootView = tabHistoryFragment.root
        return rootView
    }

    private fun initAdapter(list: ArrayList<HistoryTitleResponse>) {
        val recyclerView = tabHistoryFragment.rivHistory
        val adapter = TabHistoryAdapter(context, list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun getItemHistory() {
        tabHistoryFragment.swipeRefreshLayout.isRefreshing = true
        vm.getItem(onSuccess = {
            vm.list = it
            tabHistoryFragment.swipeRefreshLayout.isRefreshing = false
            initAdapter(it)
            if (it != null && it?.size > 0){
                tabHistoryFragment.txtNoData.visibility = View.GONE
            }else tabHistoryFragment.txtNoData.visibility = View.VISIBLE

        }, onFailed = {
            tabHistoryFragment.swipeRefreshLayout.isRefreshing = false
            ShowAlert.fail(pContext = activity, message = it)
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser || rootView == null) {
            return
        }
        if (!isDataLoaded()) {
            getItemHistory()
        }
    }


    override fun onRefresh() {
        getItemHistory()
    }

    private fun isDataLoaded(): Boolean {
        return vm.list != null && vm.list?.size!! > 0
    }


    companion object {
        fun newInstance(id: String?, arguments: Bundle? = null): TabHistoryFragment {
            val fragment = TabHistoryFragment()
            fragment.arguments = arguments
            fragment.vm.id = id
            return fragment
        }


    }
}

