package com.support.customer.lands.viewcontroller.profile

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityBookedBinding
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.CommonUtil
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewmodel.BookedViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity
import kotlinx.android.synthetic.main.activity_booked.*

class BookedActivity : BaseActivity() {

    private lateinit var bookedBinding: ActivityBookedBinding
    private var vm = BookedViewModel()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getContentView(): View {
        bookedBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_booked, null, false)

        initView()
        bookedBinding.btnSave.setOnClickListener {
            booked()
        }
        return bookedBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_profile_booked))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView(){
        UserServices?.userInfo?.let {
            bookedBinding.telFullName.setText(it.fullname)
            bookedBinding.telEmail.setText(it.email)
            bookedBinding.telPhone.setText(it.phone)
        }
        bookedBinding.telDay.isFocusable = true
        bookedBinding.telDay.showSoftInputOnFocus = false

        bookedBinding.telDay.setOnClickListener {
            CommonUtil.showDatePickerDialog(this@BookedActivity, bookedBinding.telDay)
        }

        bookedBinding.telTime.isFocusable = true
        bookedBinding.telTime.showSoftInputOnFocus = false

        bookedBinding.telTime.setOnClickListener {
            CommonUtil.showTimePickerDialog(this@BookedActivity, bookedBinding.telTime)
        }
    }

    private fun booked(){
        ShowLoading.show(this@BookedActivity)
        vm.fullName = telFullName.text.toString()
        vm.email = telEmail.text.toString()
        vm.phone = telPhone.text.toString()
        vm.timeView = telDay.text.toString() + " " + telTime.text.toString()
        vm.note = telNote.text.toString()
        vm.booked(onSuccess = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = this@BookedActivity, dialogTitle = getString(R.string.alert_title_inform), message = it, onClick = {
                finish()
            })
        }, onFailed = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = this@BookedActivity, message = it)
        })
    }
}
