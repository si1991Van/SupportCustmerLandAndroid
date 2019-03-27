package haiphat.com.bds.nghetuvan.view

import android.support.v4.app.Fragment
import com.support.customer.lands.model.ItemSaleProjectResponse

abstract class BaseFragment : Fragment(){
    open fun onSearchClick(searchQuery: String) {

    }

    open fun onBackPressed():Boolean {
        return false
    }
}