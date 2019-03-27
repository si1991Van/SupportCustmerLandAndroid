package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.OauthApi

class BookedViewModel {

    var fullName: String? = null
    var phone: String? = null
    var email: String? = null
    var timeView: String? = null
    var note: String? = null

    fun booked(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        OauthApi().postBooked(fullName, phone, email, timeView, note, onResponse = {
            var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }
}