package com.support.customer.lands.viewcontroller.auth

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityLoginBinding
import com.support.customer.lands.fcm.NotificationService
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.utills.extensions.hideSoftKeyboard
import com.support.customer.lands.utills.validation.Validator
import com.support.customer.lands.viewcontroller.home.ProjectActivity
import com.support.customer.lands.viewmodel.LoginViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity

class LoginActivity : BaseActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private var vm = LoginViewModel()

    override fun getContentView(): View {
        loginBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_login, null, false)

        loginBinding.btnSignIn.setOnClickListener {
            loginEmail()
        }
        loginBinding.txtForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
        return loginBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderVisibility(View.GONE)

    }

    private fun loginEmail() {
        vm.username = loginBinding.telUsername.text.toString().trim()
        vm.password = loginBinding.telPassword.text.toString().trim()

        if (Validator().validate(loginBinding)) {
            ShowLoading.show(this@LoginActivity)
            vm.loginEmail(onSuccess = {
                NotificationService.sendRegistrationToServer(this@LoginActivity)
                startActivity(Intent(this@LoginActivity, ProjectActivity::class.java))
                finish()
                ShowLoading.dismiss()
            }, onFailed = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this@LoginActivity, message = it)
            })
        }

    }
}
