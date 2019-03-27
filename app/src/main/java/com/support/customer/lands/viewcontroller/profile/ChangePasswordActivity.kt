package com.support.customer.lands.viewcontroller.profile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityChangePasswordBinding
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewmodel.ChangeViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity

class ChangePasswordActivity : BaseActivity() {

    private lateinit var changePasswordBinding: ActivityChangePasswordBinding
    private var vm = ChangeViewModel()

    override fun getContentView(): View {
        changePasswordBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_change_password, null, false)
        changePasswordBinding.btnSave.setOnClickListener {
            vm.odlPassword = changePasswordBinding.telOldPassword.text.toString()
            vm.newPassword = changePasswordBinding.telNewPassword.text.toString()
            vm.newConfirmPassword = changePasswordBinding.telNewConfirmPassword.text.toString()
            ShowLoading.show(this@ChangePasswordActivity)
            vm.changePassword(onSuccess = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, dialogTitle = getString(R.string.alert_title_inform), message = it, onClick = {
                    finish()
                })
            }, onFailed = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, message = it)
            })
        }

        return changePasswordBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_change_password_title))
    }


}
