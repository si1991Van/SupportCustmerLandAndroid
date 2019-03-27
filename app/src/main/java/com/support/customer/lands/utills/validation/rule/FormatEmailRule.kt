package haiphat.com.bds.nghetuvan.utils.validation.rule

import android.text.TextWatcher
import android.widget.TextView
import com.support.customer.lands.utills.extensions.isEmail

class FormatEmailRule(view: TextView, errorMessage: CharSequence) : BaseRule<TextView>(view, errorMessage), TextWatcher {

    override val isValid: Boolean
        get() {
            return this.view.text.toString().trim().isEmail()
        }

}
