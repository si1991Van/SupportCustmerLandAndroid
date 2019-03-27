package haiphat.com.bds.nghetuvan.utils.validation.rule

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import haiphat.com.bds.nghetuvan.utils.helper.EditTextHelper

abstract class BaseRule<V : TextView>(protected var view: V, private var errorMessage: CharSequence) : TextWatcher {

    protected abstract val isValid: Boolean

    init {
        this.view.addTextChangedListener(this)
    }

    fun validate(): Boolean {
        val result = this.isValid
        this.onUpdatedResultValidation(result)
        return result
    }

    private fun onUpdatedResultValidation(isError: Boolean) {
        if (isError) {
            EditTextHelper().removeError(this.view)
        } else {
            EditTextHelper().setError(this.view, this.errorMessage.toString())
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        EditTextHelper().removeError(this.view)
    }
}