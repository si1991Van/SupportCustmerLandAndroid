package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.model.ItemSaleProjectResponse
import com.support.customer.lands.model.SaleProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.ProjectApi

class TabUserViewModel {
    var transactionId: String? = null
    var note: String? = null
    var rank: Int? = 0
    var saleId: String? = null
    var id: String? = null

    fun getSale(onSuccess : (ArrayList<ItemSaleProjectResponse>?) ->Unit, onFailed : (String?) -> Unit){
        ProjectApi().postSaleProject(id, onResponse = {
            var response = GsonUtil.fromJson(it.responseContent, SaleProjectResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.data)
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }

    fun postFeedback(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        ProjectApi().postFeedback(id, transactionId, saleId, rank.toString(), note, onResponse = {
            var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }

}