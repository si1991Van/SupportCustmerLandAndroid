package com.support.customer.lands.viewcontroller.home.tab.tab_news_paper

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.adapter.NewPaperItemAdapter
import com.support.customer.lands.databinding.FragmentProjectProgressBinding
import com.support.customer.lands.model.NewsPaperItemResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.viewmodel.TabNewsViewModel
import haiphat.com.bds.nghetuvan.view.BaseFragment

class NewsPaperItemFragment: BaseFragment() , SwipeRefreshLayout.OnRefreshListener{


    private lateinit var dataBindingFragmentNewsInfo: FragmentProjectProgressBinding
    var vm = TabNewsViewModel()
    var rootView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBindingFragmentNewsInfo = DataBindingUtil.inflate(inflater, R.layout.fragment_project_progress, container, false)
        rootView = dataBindingFragmentNewsInfo.root
        getItem()
        dataBindingFragmentNewsInfo.swipeRefreshLayout.setOnRefreshListener(this)
        return rootView
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (!isVisibleToUser || rootView == null) {
//            return
//        }
//        if (!isDataLoaded()) {
//            getItem()
//        }
//    }

    private fun initAdapter(){
        val recyclerView = dataBindingFragmentNewsInfo.rivProjectProgress
        val adapter = NewPaperItemAdapter(vm.list, onClick = {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra(IntentActionKeys.KEY_NEWS_DETAIL, it?.let { it1 -> GsonUtil.toJson(it1) })
            startActivity(intent)

        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    private fun getItem(){
        if(isDataLoaded()){
            initAdapter()
            dataBindingFragmentNewsInfo.swipeRefreshLayout.isRefreshing = false
            return
        }else{
            loadData()
        }
    }


    private fun loadData(){
        dataBindingFragmentNewsInfo.swipeRefreshLayout.isRefreshing = true
        vm.getItem(onSuccess = {
            dataBindingFragmentNewsInfo.swipeRefreshLayout.isRefreshing = false
            vm.list = it
            initAdapter()
            if (it != null && it?.size > 0){
                dataBindingFragmentNewsInfo.txtNoData.visibility = View.GONE
            }else dataBindingFragmentNewsInfo.txtNoData.visibility = View.VISIBLE
        }, onFailed = {
            dataBindingFragmentNewsInfo.swipeRefreshLayout.isRefreshing = false
            ShowAlert.fail(pContext = activity, message = it)
        })
    }

    override fun onRefresh() {
        loadData()
    }

    private fun isDataLoaded(): Boolean {
        return vm.list != null && vm.list?.size!! > 0
    }


    companion object {
        fun newInstance(id: String?, projectId: String? ,arguments: Bundle? = null): NewsPaperItemFragment {
            val fragment = NewsPaperItemFragment()
            fragment.vm.categoryId = id
            fragment.vm.projectId = projectId
            fragment.arguments = arguments
            return fragment
        }
    }
}