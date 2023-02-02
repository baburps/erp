package com.software.erp.common.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.*
import com.software.erp.R
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewTextBoxBinding
import java.util.*

@InverseBindingMethods(
    InverseBindingMethod(
        type = CustomTextBox::class,
        attribute = "android:text"
    )
)
class CustomTextBox(context: Context, var attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var binding: CustomViewTextBoxBinding

    companion object {
        const val TAG = "CustomTextBox"
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomViewTextBoxBinding.inflate(inflater, this, true)
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
                binding.mTVCustomTextTitle.text = title
            } ?: run {
                binding.mTVCustomTextTitle.text = ""
            }
            val value = attributes.getString(R.styleable.custom_prop_value)
            value?.let {
                LoggerUtils.debug(TAG, value)
                binding.mTVCustomTextValue.text = value
            } ?: run {
                binding.mTVCustomTextValue.text = ""
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            LoggerUtils.debug(TAG, "attributes.recycle()")
            attributes.recycle()
        }
    }

    fun setValue(value: String?) {
        LoggerUtils.debug(TAG, "setValue$value")
        value?.let {
            binding.mTVCustomTextValue.text = ": $it"
        }
    }

    fun setTitle(value: String?) {
        LoggerUtils.debug(TAG, "setTitle$value")
        value?.let {
            binding.mTVCustomTextTitle.text = it
        }
    }

}