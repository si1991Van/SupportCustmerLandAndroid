package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.LoginResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.services.api.OauthApi

class LoginViewModel {
    var password : String? = null
    var username : String? = null

    fun loginEmail(onSuccess : () ->Unit, onFailed : (String?) -> Unit){
        OauthApi().login(username, password, onResponse = {
            val response = GsonUtil.fromJson(it.responseContent, LoginResponse::class.java)
            if (it.isSuccess()){
                UserServices.saveUserInfo(response?.data)
                onSuccess()
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }
}