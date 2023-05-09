package com.albertsons.acronyms.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.albertsons.acronyms.exception.ExceptionType

/**
 * Binding adapter used to hide the spinner once there is network error.
 */
@BindingAdapter("exceptionType")
fun hideIfError(view: View, exceptionType: ExceptionType) {
    if (exceptionType != ExceptionType.NONE) {
        view.visibility = View.GONE
    }
}