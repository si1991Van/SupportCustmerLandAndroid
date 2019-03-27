package crm.com.vn.Adapter.View

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.andexert.library.RippleView
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.support.customer.lands.R
import com.support.customer.lands.model.HistoryResponse
import com.support.customer.lands.model.HistoryTitleResponse

class HistoryTitleViewHolder(itemView: View) : ParentViewHolder<HistoryTitleResponse, HistoryResponse>(itemView) {

    private val txtType: TextView
    private val rippleItem: RippleView
    private val image: ImageView

    var isSeclect : Boolean = false


    init {
        txtType = itemView.findViewById(R.id.txtTitle) as TextView
        rippleItem = itemView.findViewById(R.id.rippleItem) as RippleView
        image = itemView.findViewById(R.id.image) as ImageView

        rippleItem.setOnRippleCompleteListener {
            isSeclect = if (isSeclect){
                setDoc()
                false
            }else{
                setNgang()
                true
            }

        }
    }

    private fun setDoc(){
        image.pivotX = (image.width / 2).toFloat()
        image.pivotY = (image.height / 2).toFloat()
        image.rotation = 0F
    }
    private fun setNgang(){
        //to make rotation use next code
        image.pivotX = (image.width / 2).toFloat()
        image.pivotY = (image.height / 2).toFloat()
        image.rotation = 180F
    }

    fun bind(response: HistoryTitleResponse?) {
        txtType.text = response?.name
    }
}