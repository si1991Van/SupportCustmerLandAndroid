package com.support.customer.lands.viewcontroller.profile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.adapter.SettingAdapter
import com.support.customer.lands.databinding.ActivitySettingBinding
import com.support.customer.lands.model.SettingResponse
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewmodel.SettingViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity

class SettingActivity : BaseActivity() {

    private lateinit var settingBinding: ActivitySettingBinding
    private var vm = SettingViewModel()

    override fun getContentView(): View {
        settingBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_setting, null, false)
        return settingBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_profile_setting))
        getItem()
    }

    private fun getItem(){
        ShowLoading.show(this@SettingActivity)
        vm.getItem(onSuccess = {
            ShowLoading.dismiss()
            initAdapter(it)
            if (it != null && it?.size > 0){
                settingBinding.txtNoData.visibility = View.GONE
            }else settingBinding.txtNoData.visibility = View.VISIBLE
        }, onFailed = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = this, message = it)
        })
    }



    private fun initAdapter(list: ArrayList<SettingResponse>?){
        val recyclerView = settingBinding.rivSetting
        val adapter = SettingAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this@SettingActivity)
        recyclerView.adapter = adapter
    }
}
