package haiphat.com.bds.nghetuvan.utils.validation.rule

import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView

class FormatPhoneRule(view: TextView, errorMessage: CharSequence) : BaseRule<TextView>(view, errorMessage), TextWatcher {

    override val isValid: Boolean
        get() {
            return Patterns.PHONE.matcher(view.text).matches()
        }

}