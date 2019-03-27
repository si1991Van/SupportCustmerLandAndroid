package com.support.customer.lands.utills.validation

import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import haiphat.com.bds.nghetuvan.utils.helper.TagViewHelpler
import haiphat.com.bds.nghetuvan.utils.validation.rule.BaseRule

/**
 * Created by DEV-01 on 12/27/2017.
 */
class Validator {
    fun validate(viewDataBinding: ViewDataBinding?): Boolean {
        return viewDataBinding != null && isValid(viewDataBinding)
    }

    private fun getViewsWithValidation(viewDataBinding: ViewDataBinding): List<View> {
        return if (viewDataBinding.root is ViewGroup) {
            TagViewHelpler().getViewsByTag(viewDataBinding.root as ViewGroup, R.id.validation)
        } else listOf(viewDataBinding.root)
    }

    private fun isValid(viewDataBinding: ViewDataBinding): Boolean {
        var result = true
        val viewWithValidations = getViewsWithValidation(viewDataBinding)
        for (viewWithValidation in viewWithValidations) {
            var viewValid = true
            val rules = viewWithValidation.getTag(R.id.validation) as List<BaseRule<*>>

            for (rule in rules) {
                viewValid = viewValid && rule.validate()
                result = result && viewValid
            }

        }
        return result
    }
}
