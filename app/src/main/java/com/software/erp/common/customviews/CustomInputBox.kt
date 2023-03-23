package com.software.erp.common.customviews

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.*
import com.software.erp.R
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewInputboxBinding
import java.util.*

@InverseBindingMethods(
    InverseBindingMethod(
        type = CustomInputBox::class ,
        attribute = "android:text"
    )
)
class CustomInputBox(context: Context , var attrs: AttributeSet?) : LinearLayout(context , attrs) {

    private var binding: CustomViewInputboxBinding

    companion object {
        const val TAG = "CustomInputBox"

        @JvmStatic
        @InverseBindingAdapter(attribute = "android:text")
        fun getInputText(customInputBox: CustomInputBox?): String {
            customInputBox?.let {
                return it.getInputValue()
            } ?: run {
                return ""
            }
        }

        @BindingAdapter("android:textAttrChanged")
        @JvmStatic
        fun setListeners(
            customInputBox: CustomInputBox? ,
            attrChange: InverseBindingListener
        ) {
            customInputBox?.binding?.mETCustomInput?.doAfterTextChanged {
                attrChange.onChange()
            }
        }
    }

    private fun setInputValue(value: String) {
        LoggerUtils.debug(TAG , value)
        binding.mETCustomInput.setText(value)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomViewInputboxBinding.inflate(inflater , this , true)
        mapAttributes()
    }

    private fun mapAttributes() {
        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs , R.styleable.custom_prop , 0 , 0
        )

        try {
            val title = attributes.getString(R.styleable.custom_prop_title)
            title?.let {
                LoggerUtils.debug(TAG , title)
                binding.mTVCustomInputTitle.text = title
            } ?: run {
                binding.mTVCustomInputTitle.text = ""
            }
            val hint = attributes.getString(R.styleable.custom_prop_hint)
            hint?.let {
                LoggerUtils.debug(TAG , hint)
                binding.mETCustomInput.hint = hint
            }
            val error = attributes.getString(R.styleable.custom_prop_error)
            error?.let {
                LoggerUtils.debug(TAG , error)
                binding.mTVCustomInputError.text = error
            } ?: run {
                binding.mTVCustomInputError.text = ""
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG , it) }
        } finally {
            LoggerUtils.debug(TAG , "attributes.recycle()")
            attributes.recycle()
        }
    }

    fun getInputValue(): String {
        return binding.mETCustomInput.text.toString()
    }

    fun setType(type: String?) {
        LoggerUtils.debug(TAG , "setType$type")
        type?.let { handleFiledType(it) }
    }


    fun setText(value: String?) {
        LoggerUtils.debug(TAG , "setText$value")
        value?.let {
            binding.mETCustomInput.setText(it)
        }
    }

    fun showError(showError: Boolean) = if (showError) {
        binding.mTVCustomInputError.visibility = View.VISIBLE
    } else {
        binding.mTVCustomInputError.visibility = View.GONE
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleFiledType(type: String) {
        when (type) {
            CustomAttributes.DATE -> {
                //Make filed only clickable to select date from date picker
                binding.mETCustomInput.setOnFocusChangeListener { _ , focused ->
                    if (focused) {
                        openDatePicker()
                    }
                }

                binding.mETCustomInput.setOnClickListener {
                    LoggerUtils.debug(TAG , "mETCustomInput click")
                }
/*
                binding.mETCustomInput.setOnTouchListener { v, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            openDatePicker()
                        }
                    }
                    v?.onTouchEvent(event) ?: true
                }
*/
            }

            CustomAttributes.NUMBERS -> {
                binding.mETCustomInput.inputType = InputType.TYPE_CLASS_NUMBER
            }

            CustomAttributes.DECIMAL_NUMBERS -> {
                binding.mETCustomInput.inputType =
                    InputType.TYPE_CLASS_NUMBER or
                            InputType.TYPE_NUMBER_FLAG_DECIMAL

            }
            CustomAttributes.NON_EDITABLE_HIGHLIGHT -> {
                binding.mETCustomInput.isEnabled = false
                binding.mETCustomInput.isClickable = false
                binding.mETCustomInput.setTextColor(ContextCompat.getColor(context , R.color.text_highlight_color))
                binding.mTVCustomInputTitle.setTextColor(ContextCompat.getColor(context , R.color.text_highlight_color))
            }

            CustomAttributes.NON_EDITABLE -> {
                binding.mETCustomInput.isEnabled = false
                binding.mETCustomInput.isClickable = false
                binding.mETCustomInput.setTextColor(ContextCompat.getColor(context , R.color.black))
            }
        }
    }

    private fun openDatePicker() {
        LoggerUtils.debug(TAG , "openDatePicker")
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context , { _ , years , monthOfYear , dayOfMonth ->
            LoggerUtils.debug(
                TAG ,
                "onDateSelection-year=$years,monthOfYear=$monthOfYear,dayOfMonth=$dayOfMonth"
            )
            binding.mETCustomInput.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1).toString() + "-" + years.toString())

            binding.mETCustomInput.clearFocus()
        } , year , month , day)

        datePickerDialog.show()
    }

    fun getInputBox(): EditText {
        return binding.mETCustomInput
    }


}