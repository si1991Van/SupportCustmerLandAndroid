package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.model.ListManagerResponse
import com.support.customer.lands.model.ManagerResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.ManagerApi

class TabManagerViewModel {

    var id: String? = null
    var list : ArrayList<ManagerResponse>? = null

    fun getItem(onSuccess : (ArrayList<ManagerResponse>)-> Unit, onFailed : (String?) -> Unit){
        ManagerApi().getListManager {
            var response = GsonUtil.fromJson(it.responseContent, ListManagerResponse::class.java)
            if (it.isSuccess()){
                response?.data?.let { onSuccess(it) } ?: onFailed(response?.message)
            }else {
                onFailed(it.getErrorMessage())
            }
        }

    }


    fun destroy(onSuccess: (String?) -> Unit, onFailed: (String?) -> Unit){
        ManagerApi().destroy(id, onResponse = {
            var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }

}