package com.support.customer.lands.model

open class BaseListResponse<T> {
    var status: Int? = 0
    var data: ArrayList<T>? = null
    var message: String? = null
}