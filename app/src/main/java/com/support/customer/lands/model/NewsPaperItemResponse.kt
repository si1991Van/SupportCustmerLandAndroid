package com.support.customer.lands.model


class ListNewsResponse: BaseListResponse<NewsPaperItemResponse>()

class NewsPaperItemResponse (
    var id: Int? = 0,
    var title: String? = null,
    var image_url: String? = null,
    var created_at: String? = null,
    var category_id: String? = null,
    var project_id: String? = null,
    var content: String? = null,
    var description: String? = null,
    var slug: String? = null

)