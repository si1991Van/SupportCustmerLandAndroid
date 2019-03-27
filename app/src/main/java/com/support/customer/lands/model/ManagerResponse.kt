package com.support.customer.lands.model


class ListManagerResponse: BaseListResponse<ManagerResponse>()


class ManagerResponse (
    var id: String? = null,
    var title: String? = null,
    var product_type: String? = null,
    var land_type: String? = null,
    var address: String? = null,
    var city_id: String? = null,
    var district_id: String? = null,
    var area: Float? = 0f,
    var area_type: String? = null,
    var price: Float? = 0f,
    var price_type: String? = null,
    var description: String? = null,
    var fullname: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var status: Int? = 0,
    var created_at: String? = null,
    var images: String? = null

)


