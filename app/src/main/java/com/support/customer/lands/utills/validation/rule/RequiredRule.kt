package haiphat.com.bds.nghetuvan.utils.validation.rule

import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView

/**
 * Created by DEV-01 on 12/27/2017.
 */
class RequiredRule(txtContent: TextView, errorMessage: CharSequence) : BaseRule<TextView>(txtContent, errorMessage), TextWatcher {
    override val isValid: Boolean
        get() = this.view != null && !TextUtils.isEmpty(view.text)

    init {
        txtContent.addTextChangedListener(this)
    }
}
