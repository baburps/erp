package com.software.erp.common.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.software.erp.R
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewInputboxBinding


class CustomInputBox(context: Context, var attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var binding: CustomViewInputboxBinding

    companion object {
        const val TAG = "CustomInputBox"

        @JvmStatic
        @BindingAdapter("input_text")
        fun setInputValue(customInputBox: CustomInputBox, value: String) {
            customInputBox.setInputValue(value)
        }
    }

    private fun setInputValue(value: String) {
        LoggerUtils.debug(TAG, value)
        binding.mETCustomInput.setText(value)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomViewInputboxBinding.inflate(inflater, this, true)
        mapAttributes()
    }

    private fun mapAttributes() {
        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.custom_prop, 0, 0
        )

        try {
            val title = attributes.getString(R.styleable.custom_prop_title)
            title?.let {
                LoggerUtils.debug(TAG, title)
                binding.mTVCustomInputTitle.text = title
            } ?: run {
                binding.mTVCustomInputTitle.text = ""
            }
            val hint = attributes.getString(R.styleable.custom_prop_hint)
            hint?.let {
                LoggerUtils.debug(TAG, hint)
                binding.mETCustomInput.hint = hint
            }
            val inputValue = attributes.getString(R.styleable.custom_prop_input_text)
            inputValue?.let {
                LoggerUtils.debug(TAG, inputValue)
                binding.mETCustomInput.setText(inputValue)
            }
            val error = attributes.getString(R.styleable.custom_prop_error)
            error?.let {
                LoggerUtils.debug(TAG, error)
                binding.mTVCustomInputError.text = error
            } ?: run {
                binding.mTVCustomInputError.text = ""
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            LoggerUtils.debug(TAG, "attributes.recycle()")
            attributes.recycle()
        }
    }

    fun getInputValue(): String {
        return binding.mETCustomInput.text.toString()
    }

    fun showError(showError: Boolean) = if (showError) {
        binding.mTVCustomInputError.visibility = View.VISIBLE
    } else {
        binding.mTVCustomInputError.visibility = View.GONE
    }

}