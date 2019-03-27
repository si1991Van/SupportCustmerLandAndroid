package com.support.customer.lands.services.api

import com.support.customer.lands.services.Config
import com.support.customer.lands.services.DgmResponse
import java.util.HashMap

class ProjectApi : BaseApi(){

    override fun apiUrl(): String? {
        return "project/"
    }

    override fun getEndPoint(): String? {
        return Config.API_URL
    }

    fun getItem(onResponse: (DgmResponse) -> Unit) {
        this["list-project",  onResponse]
    }

    fun postRatting(id: String?, rankProject: String?,onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        data.put("rank_project", rankProject ?: "")
        this.post("post-rank-project", data, onResponse)

    }

    fun postHistoryPayment(id: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        this.post("list-history-payment", data, onResponse)

    }

    fun postSaleProject(id: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        this.post("list-sale-project", data, onResponse)
    }

    fun postFeedback(id: String?, transactionId: String?, saleId: String?, rankSale: String?,
                     note: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        data.put("transaction_id", transactionId ?: "")
        data.put("sale_id", saleId ?: "")
        data.put("rank_sale", rankSale ?: "")
        data.put("note", note ?: "")
        this.post("post-feedback", data, onResponse)
    }
}