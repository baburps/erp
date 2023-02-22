package com.software.erp.view.knitting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.common.constant.ConstantUtils
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewKnittingFabricStructureBinding

class KnittingFabricStructureView(context: Context , private val attrs: AttributeSet) :
    LinearLayout(context , attrs) {

    private var fabricDiaList: MutableList<FabricDia>? = null
    private var fabricDiaAdapter: FabricDiaAdapter? = null
    private var binding: CustomViewKnittingFabricStructureBinding
    lateinit var fabricStructureSelectionListener: CustomSpinnerBox.SpinnerSelection
    lateinit var mFabricStructurePO: FabricStructurePO


    companion object {
        const val TAG = "KnittingFabricStructureView"

        /*  @JvmStatic
          @InverseBindingAdapter(attribute = "app:fabricStructurePO")
          fun getFabricStructurePO(customView: KnittingFabricStructureView?): FabricStructurePO? {
              customView?.let {
                  return it.getFabricStructurePO()
              } ?: run {
                  return null
              }
          }

          @BindingAdapter("app:fabricStructurePOAttrChanged")
          @JvmStatic
          fun setListeners(
              customView: KnittingFabricStructureView? ,
              attrChange: InverseBindingListener
          ) {
              customView?.binding?.mIBKnittingGage?.getInputBox()?.doAfterTextChanged {
                  attrChange.onChange()
              }
              customView?.binding?.mIBKnittingLoopLength?.getInputBox()?.doAfterTextChanged {
                  attrChange.onChange()
              }
          }*/
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomViewKnittingFabricStructureBinding.inflate(inflater , this , true)
        initView()
        binding.fabricStructureSelectionListener = fabricStructureSelectionListener
        binding.fabricStructureList = ConstantUtils.getFabricStructureList()
    }

    private fun initView() {
        fabricStructureSelectionListener =
            object : CustomSpinnerBox.SpinnerSelection {
                override fun onSpinnerItemSelection(selectedItem: String?) {
                    LoggerUtils.debug(TAG , "fabricStructureSelectionListener-- onSpinnerItemSelection")
                    selectedItem?.let {
                        //Save selection to PO
                        mFabricStructurePO.fabricStructure = it
                    }
                }
            }

        binding.mRVKnittingFabricStructureDia.layoutManager = LinearLayoutManager(context)
        //Disable list scroll. It will use page scroll
        binding.mRVKnittingFabricStructureDia.isNestedScrollingEnabled = false

        binding.mTVKnittingDiaAdd.setOnClickListener {
            addFabricDiaItem()
        }

        binding.mIBKnittingGage.getInputBox().addTextChangedListener {
            mFabricStructurePO.machineGage = it.toString()
        }
        binding.mIBKnittingLoopLength.getInputBox().addTextChangedListener {
            mFabricStructurePO.loopLength = it.toString()
        }
    }

    private fun addFabricDiaItem() {
        LoggerUtils.debug(TAG , "addFabricDiaItem")
        fabricDiaList?.let {
            it.add(FabricDia())
            fabricDiaAdapter?.updateList(it) ?: run {
                fabricDiaAdapter = FabricDiaAdapter(it)
                binding.mRVKnittingFabricStructureDia.adapter = fabricDiaAdapter
            }
        }
    }

    fun setFabricStructurePO(fabricStructurePO: FabricStructurePO) {
        this.mFabricStructurePO = fabricStructurePO
        fabricDiaList = fabricStructurePO.fabricDiaList
        binding.fabricStructurePO = fabricStructurePO

        //Add one Dia Item by default
        addFabricDiaItem()
    }
}