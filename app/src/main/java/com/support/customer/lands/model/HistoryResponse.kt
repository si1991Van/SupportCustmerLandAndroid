package com.support.customer.lands.model

import com.bignerdranch.expandablerecyclerview.model.Parent


class ListHistoryResponse: BaseListResponse<HistoryTitleResponse>()

class HistoryTitleResponse(
    var id : String? = null,
    val name: String?= null,
    var children: List<HistoryResponse>? = null) :
    Parent<HistoryResponse?> {

    val isVegetarian: Boolean
        get() {
            children?.let {
                for (ingredient in it) {
                    if (!ingredient?.isVegetarian!!) {
                        return false
                    }
                }
            }
            return true
        }

    override fun getChildList(): List<HistoryResponse>? {
        return children
    }

    override fun isInitiallyExpanded(): Boolean {
        return false
    }

    fun getHistoryTitleResponse(position: Int): HistoryResponse? {
        return children?.get(position)
    }
}


class HistoryResponse(
    var id : String? = null,
    var name: String? = null,
    var date_payment: String? = null,
    var status: Int? = null,
    var isVegetarian: Boolean? = false

)