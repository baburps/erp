package com.software.erp.view.knitting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.common.constant.ConstantUtils
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewKnittingFabricStructureBinding

class KnittingFabricStructureView(context: Context , private val attrs: AttributeSet) :
    LinearLayout(context , attrs) {

    private val fabricDiaList = mutableListOf<FabricDia>()
    private var fabricDiaAdapter: FabricDiaAdapter? = null
    private var binding: CustomViewKnittingFabricStructureBinding
    lateinit var fabricStructureSelectionListener: CustomSpinnerBox.SpinnerSelection
    lateinit var mFabricStructurePO: FabricStructurePO


    companion object {
        const val TAG = "KnittingFabricStructureView"
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomViewKnittingFabricStructureBinding.inflate(inflater , this , true)
        binding.fabricStructureList = ConstantUtils.getFabricStructureList()
        initView()
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
        //Add one item by default
        addFabricDiaItem()
    }

    private fun addFabricDiaItem() {
        fabricDiaList.add(FabricDia())

        fabricDiaAdapter?.updateList(fabricDiaList) ?: run {
            fabricDiaAdapter = FabricDiaAdapter(fabricDiaList)
            binding.mRVKnittingFabricStructureDia.adapter = fabricDiaAdapter
        }
    }

    fun setFabricStructurePO(fabricStructurePO: FabricStructurePO) {
        this.mFabricStructurePO = fabricStructurePO
        binding.fabricStructurePO = fabricStructurePO
    }
}