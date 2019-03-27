package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.model.LoginResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.services.api.OauthApi

class ProfileViewModel {


    var fullName: String? = null
    var email: String? = null
    var birthday: String? = null

    fun updateAvatar(path : String? , onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit) {
        OauthApi().updateAvatar(path, onResponse = {
            val response = GsonUtil.fromJson(it.responseContent, LoginResponse::class.java)
            if(it.isSuccess()){
                UserServices.saveProfile(response?.data)
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }

        })
    }


    fun updateProfile(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit) {
        OauthApi().updateProfile(fullName, email, birthday, onResponse = {
            val response = GsonUtil.fromJson(it.responseContent, LoginResponse::class.java)
            if(it.isSuccess()){
                UserServices.saveProfile(response?.data)
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }

        })
    }

    fun logout(deviceId: String?, deviceToken: String?, onSuccess : () ->Unit, onFailed : (String?) -> Unit){
        OauthApi().logout(deviceId, deviceToken, onResponse = {
            if (it.isSuccess()){
                onSuccess()
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }
}