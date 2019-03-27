package haiphat.com.bds.nghetuvan.utils.validation.rule

import android.widget.TextView

/**
 * Created by DEV-01 on 1/3/2018.
 */
class MatchTextRule(view: TextView, private val compareTextView: TextView, errorMessage: CharSequence) : BaseRule<TextView>(view, errorMessage) {

    override val isValid: Boolean
        get() = view.text.toString() == compareTextView.text.toString()
}

