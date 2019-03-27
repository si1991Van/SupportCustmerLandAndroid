package com.support.customer.lands.model




class ListCategoryResponse: BaseListResponse<CategoryNewsPaperResponse>()

class CategoryNewsPaperResponse (
    var id: Int? = 0,
    var name: String? = null
)