package com.support.customer.lands.viewmodel

import android.arch.lifecycle.ViewModel
import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.model.LoginResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.services.api.OauthApi

class ChangeViewModel {
    var odlPassword : String? = null
    var newPassword : String? = null
    var newConfirmPassword : String? = null

    fun changePassword(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
       OauthApi().changePassword(odlPassword, newPassword, newConfirmPassword, onResponse = {
           var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
           if (it.isSuccess()){
               onSuccess(response?.message)
           }else{
               onFailed(it.getErrorMessage())
           }
       })
    }
}