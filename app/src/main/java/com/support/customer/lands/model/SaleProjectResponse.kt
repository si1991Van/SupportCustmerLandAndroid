package com.support.customer.lands.model

class SaleProjectResponse: BaseListResponse<ItemSaleProjectResponse>()


class ItemSaleProjectResponse{
    var id: String? = null
    var transaction_id: String? = null
    var fullname: String? = null
    var avatar: String? = null
    var name: String? = null
    var project_name: String? = null
    var rank: Int? = 0
}