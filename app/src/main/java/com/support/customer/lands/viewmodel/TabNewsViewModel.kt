package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.CategoryNewsPaperResponse
import com.support.customer.lands.model.ListCategoryResponse
import com.support.customer.lands.model.ListNewsResponse
import com.support.customer.lands.model.NewsPaperItemResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.NewsApi

class TabNewsViewModel {

    var current: Int = 20
    var page: Int = 1
    var categoryId: String? = null
    var projectId: String? = null

    var list : ArrayList<NewsPaperItemResponse>? = null

    fun getCategory(onSuccess: (ArrayList<CategoryNewsPaperResponse>?) -> Unit, onFailed: (String?) -> Unit) {
        NewsApi().getCategory {
            if (it.isSuccess()){
                val response = GsonUtil.fromJson(it.responseContent, ListCategoryResponse::class.java)
                onSuccess(response?.data)
            }else{
                onFailed(it.getErrorMessage())
            }


        }
    }

    fun getItem(onSuccess: (ArrayList<NewsPaperItemResponse>?) -> Unit, onFailed: (String?) -> Unit) {
        NewsApi().postNews(projectId, categoryId, page.toString(), current.toString(), onResponse = {
            if (it.isSuccess()){
                val response = GsonUtil.fromJson(it.responseContent, ListNewsResponse::class.java)
                onSuccess(response?.data)
            }else{
                onFailed(it.getErrorMessage())
            }


        })
    }



}


