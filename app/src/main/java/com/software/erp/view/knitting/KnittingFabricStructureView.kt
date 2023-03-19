package com.software.erp.view.knitting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.common.constant.ConstantUtils
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.CustomViewGreyFabricStructureBinding
import com.software.erp.databinding.CustomViewKnittingFabricStructureBinding
import com.software.erp.view.greyfabric.GreyFabricDia
import com.software.erp.view.greyfabric.GreyFabricStructurePO
import com.software.erp.view.knitting.model.FabricDia
import com.software.erp.view.knitting.model.FabricStructurePO

class KnittingFabricStructureView(context: Context , private val attrs: AttributeSet) :
    LinearLayout(context , attrs) {

    private var knittingBinding: CustomViewKnittingFabricStructureBinding? = null
    private var greyFabricBinding: CustomViewGreyFabricStructureBinding? = null

    private var fabricDiaAdapter: FabricDiaAdapter? = null
    lateinit var fabricStructureSelectionListener: CustomSpinnerBox.SpinnerSelection

    var mFabricStructurePO: FabricStructurePO? = null
    private var fabricDiaList: MutableList<FabricDia>? = null

    var mGreyFabricStructurePO: GreyFabricStructurePO? = null
    private var greyFabricDiaList: MutableList<GreyFabricDia>? = null

    private var pageKey: String = ""


    companion object {
        const val TAG = "KnittingFabricStructureView"
    }

    init {
        LoggerUtils.debug(TAG , "init")
    }

    fun setFabricStructurePO(fabricStructurePO: FabricStructurePO?) {
        LoggerUtils.debug(TAG , "setFabricStructurePO")
        initKnittingFabricView()

        fabricStructurePO?.let {
            this.mFabricStructurePO = fabricStructurePO
            fabricDiaList = fabricStructurePO.fabricDiaList
            knittingBinding?.fabricStructurePO = fabricStructurePO

            //Add one Dia Item by default
            addKnittingFabricDiaItem()
        }
    }

    private fun initKnittingFabricView() {
        LoggerUtils.debug(TAG , "initKnittingFabricView")

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        knittingBinding = CustomViewKnittingFabricStructureBinding.inflate(inflater , this , true)

        fabricStructureSelectionListener =
            object : CustomSpinnerBox.SpinnerSelection {
                override fun onSpinnerItemSelection(selectedItem: String?) {
                    LoggerUtils.debug(TAG , "fabricStructureSelectionListener-- onSpinnerItemSelection")
                    selectedItem?.let {
                        //Save selection to PO
                        mFabricStructurePO?.fabricStructure = it
                    }
                }
            }

        knittingBinding?.let { knittingBinding_ ->
            knittingBinding_.mRVKnittingFabricStructureDia.layoutManager = LinearLayoutManager(context)
            //Disable list scroll. It will use page scroll
            knittingBinding_.mRVKnittingFabricStructureDia.isNestedScrollingEnabled = false

            knittingBinding_.mTVKnittingDiaAdd.setOnClickListener {
                addKnittingFabricDiaItem()
            }

            knittingBinding_.mIBKnittingGage.getInputBox().addTextChangedListener {
                mFabricStructurePO?.machineGage = it.toString()
            }
            knittingBinding_.mIBKnittingLoopLength.getInputBox().addTextChangedListener {
                mFabricStructurePO?.loopLength = it.toString()
            }

            knittingBinding_.fabricStructureSelectionListener = fabricStructureSelectionListener
            knittingBinding_.fabricStructureList = ConstantUtils.getFabricStructureList()
        }
    }

    private fun addKnittingFabricDiaItem() {
        LoggerUtils.debug(TAG , "addFabricDiaItem")
        fabricDiaList?.let {
            LoggerUtils.debug(TAG , "addFabricDiaItem--fabricDiaList not null")
            //By default add a Dia field for Knitting program page
            if (isKnittingProgramPage()) {
                it.add(FabricDia())
            }
            fabricDiaAdapter?.updateList(it) ?: run {
                fabricDiaAdapter = FabricDiaAdapter(it , null)
                knittingBinding?.mRVKnittingFabricStructureDia?.adapter = fabricDiaAdapter
            }
        }
    }

    fun setGreyFabricStructurePO(greyFabricStructurePO: GreyFabricStructurePO?) {
        LoggerUtils.debug(TAG , "setGreyFabricStructurePO")
        initGreyFabricView()

        greyFabricStructurePO?.let {
            this.mGreyFabricStructurePO = greyFabricStructurePO
            greyFabricDiaList = greyFabricStructurePO.fabricDiaList

            /* val fabricStructurePO = FabricStructurePO()
             fabricStructurePO.fabricStructure = greyFabricStructurePO.fabricStructure
             fabricStructurePO.loopLength = greyFabricStructurePO.loopLength
             fabricStructurePO.machineGage = greyFabricStructurePO.machineGage
             greyFabricBinding?.fabricStructurePO = fabricStructurePO*/

            greyFabricBinding?.fabricStructurePO = greyFabricStructurePO
            addGreyFabricDiaItem()
        }
    }

    private fun initGreyFabricView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        greyFabricBinding = CustomViewGreyFabricStructureBinding.inflate(inflater , this , true)

        LoggerUtils.debug(TAG , "initView")
        fabricStructureSelectionListener =
            object : CustomSpinnerBox.SpinnerSelection {
                override fun onSpinnerItemSelection(selectedItem: String?) {
                    LoggerUtils.debug(TAG , "fabricStructureSelectionListener-- onSpinnerItemSelection")
                    selectedItem?.let {
                        //Save selection to PO
                        mFabricStructurePO?.fabricStructure = it
                    }
                }
            }

        greyFabricBinding?.let { greyFabricBinding_ ->
            greyFabricBinding_.mRVKnittingFabricStructureDia.layoutManager = LinearLayoutManager(context)
            //Disable list scroll. It will use page scroll
            greyFabricBinding_.mRVKnittingFabricStructureDia.isNestedScrollingEnabled = false

            greyFabricBinding_.mTVKnittingDiaAdd.setOnClickListener {
                addGreyFabricDiaItem()
            }

            greyFabricBinding_.mIBKnittingGage.getInputBox().addTextChangedListener {
                mFabricStructurePO?.machineGage = it.toString()
            }
            greyFabricBinding_.mIBKnittingLoopLength.getInputBox().addTextChangedListener {
                mFabricStructurePO?.loopLength = it.toString()
            }

            greyFabricBinding_.fabricStructureSelectionListener = fabricStructureSelectionListener
            greyFabricBinding_.fabricStructureList = ConstantUtils.getFabricStructureList()
        }
    }

    private fun addGreyFabricDiaItem() {
        LoggerUtils.debug(TAG , "addGreyFabricDiaItem")
        greyFabricDiaList?.let { greyFabricDiaList_ ->
            LoggerUtils.debug(TAG , "addFabricDiaItem--greyFabricDiaList not null")
            fabricDiaAdapter = FabricDiaAdapter(null , greyFabricDiaList_)
            greyFabricBinding?.mRVKnittingFabricStructureDia?.adapter = fabricDiaAdapter
        }
    }

    fun setPageKey(pageKey: String) {
        LoggerUtils.debug(TAG , "setPageKey")
        this.pageKey = pageKey
        if (isKnittingProgramPage()) {
            //Show add dia button
            knittingBinding?.mTVKnittingDiaAdd?.visibility = View.VISIBLE
        } else {
            //Hide add dia button
            greyFabricBinding?.mTVKnittingDiaAdd?.visibility = View.GONE
        }
    }

    private fun isKnittingProgramPage() = pageKey == FabricStructureAdapter.PAGE_KEY_KNITTING
}