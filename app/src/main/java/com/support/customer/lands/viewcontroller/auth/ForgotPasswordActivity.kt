package com.support.customer.lands.viewcontroller.auth

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityForgotPasswordBinding
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.utills.validation.Validator
import com.support.customer.lands.viewmodel.ForgotPasswordViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity

class ForgotPasswordActivity : BaseActivity() {

    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding
    private var vm = ForgotPasswordViewModel()

    override fun getContentView(): View {
        forgotPasswordBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_forgot_password, null, false)
        forgotPasswordBinding.btnSignIn.setOnClickListener {
            forgotPassword()
        }
        return forgotPasswordBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.text_forgot_password_title))
        setrippleRightVisibility(View.INVISIBLE)

    }



    private fun forgotPassword() {
        vm.phone = forgotPasswordBinding.telUsername.text.toString().trim()

        if (Validator().validate(forgotPasswordBinding)) {
            ShowLoading.show(this@ForgotPasswordActivity)
            vm.forgotPassword(onSuccess = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, dialogTitle = getString(R.string.alert_title_inform), message = it, onClick = {
                    finish()
                })

            }, onFailed = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this@ForgotPasswordActivity, message = it)
            })
        }

    }

}
