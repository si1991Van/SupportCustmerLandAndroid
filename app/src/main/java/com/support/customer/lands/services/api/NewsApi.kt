package com.support.customer.lands.services.api

import com.support.customer.lands.services.Config
import com.support.customer.lands.services.DgmResponse
import java.util.HashMap

class NewsApi : BaseApi(){

    override fun apiUrl(): String? {
        return "news/"
    }

    override fun getEndPoint(): String? {
        return Config.API_URL
    }

    fun getCategory(onResponse: (DgmResponse) -> Unit) {
        this["list-category",  onResponse]
    }

    fun postNews(projectId: String?, categoryId: String?, page: String?, number: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("project_id", projectId ?: "")
        data.put("category_id", categoryId ?: "")
        data.put("page", page ?: "")
        data.put("number_record", number ?: "")
        this.post("list-news", data, onResponse)

    }


}