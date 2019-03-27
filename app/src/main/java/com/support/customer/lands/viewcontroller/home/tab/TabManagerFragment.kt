package com.support.customer.lands.viewcontroller.home.tab

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.adapter.TabManagerAdapter
import com.support.customer.lands.databinding.FragmentDepositManagerBinding
import com.support.customer.lands.model.ManagerResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.IntentDataKeys
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewcontroller.home.MainActivity
import com.support.customer.lands.viewcontroller.home.CreateManagerActivity
import com.support.customer.lands.viewmodel.TabManagerViewModel
import haiphat.com.bds.nghetuvan.view.BaseFragment

class TabManagerFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener{


    private lateinit var tabManagerBinding: FragmentDepositManagerBinding
    private var vm = TabManagerViewModel()

    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tabManagerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_deposit_manager, container, false)
        (activity as MainActivity).setHeaderTitle(getString(R.string.txt_profile_massage))
        tabManagerBinding.fab.setOnClickListener {
            var intent = Intent(activity, CreateManagerActivity::class.java)
            intent.putExtra(IntentDataKeys.KEY_CREATE_MANAGER_TYPE, IntentActionKeys.CREATE_MANAGER)
            startActivityForResult(intent, IntentActionKeys.CREATE_MANAGER)
        }
        tabManagerBinding.swipeRefreshLayout.setOnRefreshListener(this)

        if (userVisibleHint && !isDataLoaded()) {
            getManager()
        }
        rootView = tabManagerBinding.root
        return rootView
    }

    private fun initAdapter(list: ArrayList<ManagerResponse>?){
        val recyclerView = tabManagerBinding.rvManager
        val adapter = TabManagerAdapter(list, onClickEdit = {
            var intent = Intent(activity, CreateManagerActivity::class.java)
            intent.putExtra(IntentDataKeys.KEY_UPDATE_MANAGER_TYPE, IntentActionKeys.UPDATE_MANAGER)
            intent.putExtra(IntentDataKeys.KEY_MANAGER, it?.let { GsonUtil.toJson(it)})
            startActivityForResult(intent, IntentActionKeys.CREATE_MANAGER)

        }, onDelete = {
            destroyManager(it?.id)
        })
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = adapter
    }

    private fun getManager(){
        tabManagerBinding.swipeRefreshLayout.isRefreshing = true
        vm.getItem(onSuccess = {
            vm.list = it
            initAdapter(it)
            if (it != null && it?.size > 0){
                tabManagerBinding.txtNoData.visibility = View.GONE
            }else tabManagerBinding.txtNoData.visibility = View.VISIBLE
            tabManagerBinding.swipeRefreshLayout.isRefreshing = false
        }, onFailed = {
            tabManagerBinding.swipeRefreshLayout.isRefreshing = false
            ShowAlert.fail(pContext = activity, message = it)
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser || rootView == null) {
            return
        }
        if (!isDataLoaded()) {
            getManager()
        }
    }

    private fun isDataLoaded(): Boolean {
        return vm.list != null && vm.list?.size!! > 0
    }

    private fun destroyManager(id: String?){
        vm.id = id
        ShowLoading.show(activity)
        vm.destroy(onSuccess = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = activity, dialogTitle = getString(R.string.alert_title_inform), message = it, onClick = {
                getManager()
            })

        }, onFailed = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = activity, message = it)
        })
    }

    override fun onRefresh() {
        getManager()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == IntentActionKeys.RELOAD_DATA){
            getManager()
        }
    }
}