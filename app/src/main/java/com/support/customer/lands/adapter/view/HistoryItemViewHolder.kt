package crm.com.vn.Adapter.View

import android.view.View
import android.widget.TextView
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.support.customer.lands.R
import com.support.customer.lands.model.HistoryResponse


class HistoryItemViewHolder(itemView: View) : ChildViewHolder<HistoryResponse>(itemView) {


    private val txtType: TextView
    private val txtTime: TextView

    init {

        txtType = itemView.findViewById(R.id.txtType) as TextView
        txtTime = itemView.findViewById(R.id.txtDateTime) as TextView
    }

    fun bind(ingredient: HistoryResponse?) {
        txtType.text = ingredient?.name
        txtTime.text = ingredient?.date_payment

    }
}