package com.software.erp.common.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.software.erp.R
import com.software.erp.databinding.CustomViewInputboxBinding


class CustomInputBox(context: Context, var attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var mBinding: CustomViewInputboxBinding

    companion object {
        const val TAG = "CustomInputBox"
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CustomViewInputboxBinding.inflate(inflater)
        mapAttributes()
    }

    private fun mapAttributes() {
        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.custom_prop, 0, 0
        )

        try {
            val title = attributes.getString(R.styleable.custom_prop_title)
            title?.let {
                mBinding.mTVCustomInputTitle.text = title
            } ?: run {
                mBinding.mTVCustomInputTitle.text = ""
            }
            val hint = attributes.getString(R.styleable.custom_prop_hint)
            hint?.let {
                mBinding.mETCustomInput.hint = hint
            } ?: run {
                mBinding.mETCustomInput.hint = ""
            }
            val error = attributes.getString(R.styleable.custom_prop_error)
            error?.let {
                mBinding.mTVCustomInputError.text = error
            } ?: run {
                mBinding.mTVCustomInputError.text = ""
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            attributes.recycle()
        }
    }

    fun getInputValue(): String {
        return mBinding.mETCustomInput.text.toString()
    }

    fun showError(showError: Boolean) = if (showError) {
        mBinding.mTVCustomInputError.visibility = View.VISIBLE
    } else {
        mBinding.mTVCustomInputError.visibility = View.GONE
    }

}