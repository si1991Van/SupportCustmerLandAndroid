package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.ListProjectResponse
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.ProjectApi

class HomeViewModel {


    fun getItem(onSuccess : (ArrayList<ProjectResponse>?) ->Unit, onFailed : (String?) -> Unit){
        ProjectApi().getItem {
            val response = GsonUtil.fromJson(it.responseContent, ListProjectResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.data)
            }else{
                onFailed(it.getErrorMessage())
            }
        }
    }

}

