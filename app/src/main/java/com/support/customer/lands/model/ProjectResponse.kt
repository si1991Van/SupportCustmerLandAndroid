package com.support.customer.lands.model

class ProjectResponse(
    var id: String? = null,
    var image_url: String? = null,
    var name: String? = null,
    var description: String? = null,
    var email: String?= null,
    var hotline: String? = null,
    var rank_project: Int? = 0
)

class ListProjectResponse: BaseListResponse<ProjectResponse>()