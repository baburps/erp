package com.software.erp.common.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.software.erp.R
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewSpinnerBoxBinding


class CustomSpinnerBox(context: Context, private val attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    private var binding: CustomViewSpinnerBoxBinding
    private var spinnerList: List<String>? = null

    companion object {
        const val TAG = "CustomSpinnerBox"
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomViewSpinnerBoxBinding.inflate(inflater, this, true)
        mapAttributes()
        initView()
    }

    private fun initView() {

    }

    private fun mapAttributes() {
        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.custom_prop, 0, 0
        )

        try {
            val title = attributes.getString(R.styleable.custom_prop_title)
            title?.let {
                LoggerUtils.debug(TAG, title)
                binding.mTVCustomSpinnerTitle.text = title
            } ?: run {
                binding.mTVCustomSpinnerTitle.text = ""
            }

            val error = attributes.getString(R.styleable.custom_prop_error)
            error?.let {
                LoggerUtils.debug(TAG, error)
                binding.mTVCustomSpinnerError.text = error
            } ?: run {
                binding.mTVCustomSpinnerError.text = ""
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        } finally {
            LoggerUtils.debug(TAG, "attributes.recycle()")
            attributes.recycle()
        }
    }

    fun setSpinnerList(list: List<String>?) {
        LoggerUtils.debug(TAG, "setSpinnerList")
        list?.let {
            spinnerList = it
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, it)
            binding.mSpinnerCustomSpinner.adapter = adapter
        }
    }

    fun setSpinnerSelection(listener: SpinnerSelection?) {
        LoggerUtils.debug(TAG, "setSpinnerSelection")
        binding.mSpinnerCustomSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    LoggerUtils.debug(TAG, "setSpinnerSelection--onItemSelected")
                    spinnerList?.let { list ->
                        LoggerUtils.debug(
                            TAG,
                            "setSpinnerSelection--onItemSelected--spinningMillSelectionLiveData updated"
                        )
                        listener?.onSpinnerItemSelection(list[position])
                    }
                }
            }
    }

    interface SpinnerSelection {
        fun onSpinnerItemSelection(selectedItem: String?)
    }
}