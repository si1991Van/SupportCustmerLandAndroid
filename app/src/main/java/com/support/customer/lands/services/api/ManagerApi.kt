package com.support.customer.lands.services.api

import com.support.customer.lands.services.Config
import com.support.customer.lands.services.DgmResponse
import java.util.HashMap

class ManagerApi: BaseApi() {

    override fun apiUrl(): String? {
        return "consignment/"
    }

    override fun getEndPoint(): String? {
        return Config.API_URL
    }

    fun getOption(onResponse: (DgmResponse) -> Unit){
        this["get-option", onResponse]
    }

    fun getListManager(onResponse: (DgmResponse) -> Unit){
        this["list-consignment", onResponse]
    }

    fun destroy(id: String?, onResponse: (DgmResponse) -> Unit){
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        this.post("destroy-consignment", data,onResponse)
    }

    fun addConsignment(fullname: String?, phone: String?, email: String?, title: String?, product_type: String?,
                       land_type: String?, address: String?, city_id: String?, district_id: String?, area: String?,
                       area_type: String?, price: String?, price_type: String?, description: String?, images: String?,
                       onResponse: (DgmResponse) -> Unit){
        val data = HashMap<String, String>()
        data.put("fullname", fullname ?: "")
        data.put("phone", phone ?: "")
        data.put("email", email ?: "")
        data.put("title", title ?: "")
        data.put("product_type", product_type ?: "")
        data.put("land_type", land_type ?: "")
        data.put("address", address ?: "")
        data.put("city_id", city_id ?: "")
        data.put("district_id", district_id ?: "")
        data.put("area", area ?: "")
        data.put("area_type", area_type ?: "")
        data.put("price", price ?: "")
        data.put("price_type", price_type ?: "")
        data.put("description", description ?: "")
        data.put("images", images ?: "")
        this.post("add-consignment", data, onResponse)
    }

    fun updateConsignment(id: String?, fullname: String?, phone: String?, email: String?, title: String?, product_type: String?,
                       land_type: String?, address: String?, city_id: String?, district_id: String?, area: String?,
                       area_type: String?, price: String?, price_type: String?, description: String?, images: String?,
                       onResponse: (DgmResponse) -> Unit){
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        data.put("fullname", fullname ?: "")
        data.put("phone", phone ?: "")
        data.put("email", email ?: "")
        data.put("title", title ?: "")
        data.put("product_type", product_type ?: "")
        data.put("land_type", land_type ?: "")
        data.put("address", address ?: "")
        data.put("city_id", city_id ?: "")
        data.put("district_id", district_id ?: "")
        data.put("area", area ?: "")
        data.put("area_type", area_type ?: "")
        data.put("price", price ?: "")
        data.put("price_type", price_type ?: "")
        data.put("description", description ?: "")
        data.put("images", images ?: "")
        this.post("update-consignment", data, onResponse)
    }


}