package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.OauthApi

class ForgotPasswordViewModel {
    var phone : String? = null

    fun forgotPassword(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        OauthApi().forgotPassword(phone, onResponse = {
            val response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
            if (it.isSuccess()){

                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }
}