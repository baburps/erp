package com.software.erp.common.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.software.erp.R
import kotlinx.android.synthetic.main.custom_view_inputbox.view.*


class CustomInputBox(context: Context, var attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var view: View

    companion object {
        const val TAG = "CustomInputBox"
    }

    init {
        view = View.inflate(context, R.layout.custom_view_inputbox, this)
        mapAttributes()
    }

    private fun mapAttributes() {
        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.custom_prop, 0, 0
        )

        try {
            val title = attributes.getString(R.styleable.custom_prop_title)
            title?.let {
                view.mTVCustomInputTitle.text = title
            } ?: run {
                view.mTVCustomInputTitle.text = ""
            }
            val hint = attributes.getString(R.styleable.custom_prop_hint)
            hint?.let {
                view.mETCustomInput.hint = hint
            } ?: run {
                view.mETCustomInput.hint = ""
            }
            val error = attributes.getString(R.styleable.custom_prop_error)
            error?.let {
                view.mTVCustomInputError.text = error
            } ?: run {
                view.mTVCustomInputError.text = ""
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            attributes.recycle()
        }
    }

    fun getInputValue(): String {
        return view.mETCustomInput.text.toString()
    }

    fun showError(showError: Boolean) = if (showError) {
        view.mTVCustomInputError.visibility = View.VISIBLE
    } else {
        view.mTVCustomInputError.visibility = View.GONE
    }

}