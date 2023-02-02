package com.dev.ecommerce.common.bindingadapter

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter


@BindingAdapter("onFocusChange")
fun onFocusChange(text: EditText, listener: View.OnFocusChangeListener?) {
    text.onFocusChangeListener = listener
}

@BindingAdapter("onClick")
fun onClick(button: Button, listener: View.OnClickListener?) {
    button.setOnClickListener(listener)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}