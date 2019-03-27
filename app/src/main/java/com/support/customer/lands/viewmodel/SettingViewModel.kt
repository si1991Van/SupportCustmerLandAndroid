package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.ListSettingResponse
import com.support.customer.lands.model.LoginResponse
import com.support.customer.lands.model.SettingResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.services.api.OauthApi

class SettingViewModel {

    fun postSetting(id: String?, status: Boolean?, onSuccess : () ->Unit, onFailed : (String?) -> Unit){
        OauthApi().postSetting(id, status.toString(), onResponse = {
            if (it.isSuccess()){
                onSuccess
            }else{
                onFailed(it.getErrorMessage())
            }
        })

    }

    fun getItem(onSuccess: (ArrayList<SettingResponse>?) -> Unit?, onFailed: (String?) -> Unit?){
        OauthApi().getSetting {
            if (it.isSuccess()){
                val response = GsonUtil.fromJson(it.responseContent, ListSettingResponse::class.java)
                onSuccess(response?.data)
            }else{
                onFailed(it.getErrorMessage())
            }
        }
    }


}