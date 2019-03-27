package haiphat.com.bds.nghetuvan.utils.helper

import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.view.View
import android.widget.TextView

class EditTextHelper {
    fun removeError(textView: TextView) {
        setError(textView, null)
    }

    fun setError(textView: TextView, errorMessage: String?) {
        val textInputLayout = getTextInputLayout(textView)
        if (textInputLayout != null) {
            textInputLayout.isErrorEnabled = !TextUtils.isEmpty(errorMessage)
            textInputLayout.error = errorMessage
        } else {
            textView.error = errorMessage
        }
    }

    private fun getTextInputLayout(textView: TextView): TextInputLayout? {
        var textInputLayout: TextInputLayout? = null

        var parent = textView.parent
        while (parent is View) {
            if (parent is TextInputLayout) {
                textInputLayout = parent
                break
            }
            parent = parent.getParent()
        }
        return textInputLayout
    }
}