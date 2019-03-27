package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.ProjectApi

class TabPhoneViewModel {
    var projectResponse: ProjectResponse? = null
//    var rankPrject: Int? = 0

    fun postRatting(rank_project: Int,onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        ProjectApi().postRatting(projectResponse?.id, rank_project.toString(), onResponse = {
            var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }
        })
    }

}